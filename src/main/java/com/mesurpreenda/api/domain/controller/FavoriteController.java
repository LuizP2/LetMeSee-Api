package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.service.UserService;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourite")
public class FavoriteController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public FavoritesDTO getFavorites(@PathVariable Long id) {
        return userService.getFavorites(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addFavorite(@PathVariable Long id, @RequestParam Long contentId, @RequestParam boolean isMovie) {
        userService.addFavorite(id, contentId, isMovie);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long id, @RequestParam Long contentId, @RequestParam boolean isMovie) {
        userService.removeFavorite(id, contentId, isMovie);
        return ResponseEntity.ok("Removed from favorites");
    }
}

