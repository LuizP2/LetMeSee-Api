package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.repository.UserRepository;
import com.mesurpreenda.api.data.service.ApiServices;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    private ApiServices userService;
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/{id}")
    public FavoritesDTO getFavorites(@PathVariable("id") String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new FavoritesDTO(user.getFavoriteMovies(), user.getFavoriteSeries());
    }


    @PostMapping("movie/{id}")
    public ResponseEntity<String> addFavorite(@PathVariable String id, @RequestBody Movie movie) {
        userService.addMovieToFavorites(id, movie);
        return ResponseEntity.ok("Added to favorites");
    }
    @PostMapping("series/{id}")
    public ResponseEntity<String> addFavorite(@PathVariable String id, @RequestBody Series series) {
        userService.addSeriesToFavorites(id, series);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFavorite(@PathVariable String id, @RequestParam Long contentId, @RequestParam boolean isMovie) {
        userService.removeFavorite(id, contentId, isMovie);
        return ResponseEntity.ok("Removed from favorites");
    }
}

