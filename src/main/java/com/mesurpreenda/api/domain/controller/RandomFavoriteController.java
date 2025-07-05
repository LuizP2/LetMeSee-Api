package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.service.ApiServices;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/favorites/random")
public class RandomFavoriteController {

    @Autowired
    private ApiServices userService;

    @GetMapping
    public ResponseEntity<Object> randomFromFavorites(@AuthenticationPrincipal User user) {
        FavoritesDTO favorites = userService.getFavorites(user.getId());

        List<Object> combined = new ArrayList<>();
        combined.addAll(favorites.getFavoriteMovies());
        combined.addAll(favorites.getFavoriteSeries());

        if (combined.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No favorites available");
        }

        Object randomFavorite = combined.get(new Random().nextInt(combined.size()));
        return ResponseEntity.ok(randomFavorite);
    }

    @GetMapping("/movie")
    public ResponseEntity<Object> randomFavoriteMovie(@AuthenticationPrincipal User user) {
        FavoritesDTO favorites = userService.getFavorites(user.getId());
        List<Movie> favoriteMovies = favorites.getFavoriteMovies();

        if (favoriteMovies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No favorite movies available");
        }

        Movie randomMovie = favoriteMovies.get(new Random().nextInt(favoriteMovies.size()));
        return ResponseEntity.ok(randomMovie);
    }

    @GetMapping("/series")
    public ResponseEntity<Object> randomFavoriteSeries(@AuthenticationPrincipal User user) {
        FavoritesDTO favorites = userService.getFavorites(user.getId());
        List<Series> favoriteSeries = favorites.getFavoriteSeries();

        if (favoriteSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No favorite series available");
        }

        Series randomSeries = favoriteSeries.get(new Random().nextInt(favoriteSeries.size()));
        return ResponseEntity.ok(randomSeries);
    }
}
