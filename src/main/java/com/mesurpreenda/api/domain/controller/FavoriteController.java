package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.service.ApiServices;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    private ApiServices userService;

    @GetMapping("/{id}")
    public FavoritesDTO getFavorites(@PathVariable String id) {
        return userService.getFavorites(id);
    }

    @PostMapping()
    public ResponseEntity<String> addFavorite(@PathVariable String id, @RequestBody Movie movie) {
        userService.addFavorite(id, movie);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFavorite(@PathVariable String id, @RequestParam String contentId, @RequestParam boolean isMovie) {
        userService.removeFavorite(id, contentId, isMovie);
        return ResponseEntity.ok("Removed from favorites");
    }
}

