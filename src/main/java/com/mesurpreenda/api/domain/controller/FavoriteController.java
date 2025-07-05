package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.service.ApiServices;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private ApiServices userService;

    @GetMapping
    public FavoritesDTO getMyFavorites(@AuthenticationPrincipal User user) {
        return new FavoritesDTO(user.getFavoriteMovies(), user.getFavoriteSeries());
    }


    @PostMapping("/movie")
    public ResponseEntity<String> addFavoriteMovie(@AuthenticationPrincipal User user, @RequestBody Movie movie) {
        userService.addMovieToFavorites(user.getId(), movie);
        return ResponseEntity.ok("Movie added to favorites");
    }

    @PostMapping("/series")
    public ResponseEntity<String> addFavoriteSeries(@AuthenticationPrincipal User user, @RequestBody Series series) {
        userService.addSeriesToFavorites(user.getId(), series);
        return ResponseEntity.ok("Series added to favorites");
    }

    @DeleteMapping
    public ResponseEntity<String> removeFavorite(@AuthenticationPrincipal User user, @RequestParam Long contentId, @RequestParam boolean isMovie) {
        userService.removeFavorite(user.getId(), contentId, isMovie);
        return ResponseEntity.ok("Removed from favorites");
    }
}

