package com.mesurpreenda.api.domain.controller;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.service.ApiServices;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/random")
public class RandomFavoriteController {

    @Autowired
    private ApiServices userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> randomFromFavorites(@PathVariable String userId) {
        FavoritesDTO favorites = userService.getFavorites(userId);

        List<Movie> favoriteMovies = favorites.getFavoriteMovies();
        List<Series> favoriteSeries = favorites.getFavoriteSeries();

        List<Object> combined = new ArrayList<>();
        combined.addAll(favoriteMovies);
        combined.addAll(favoriteSeries);

        if (combined.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No favorites available");
        }

        Object randomFavorite = combined.get(new Random().nextInt(combined.size()));
        return ResponseEntity.ok(randomFavorite);
    }

    @GetMapping("/movie/{userId}")
    public ResponseEntity<Object> randomFavoriteMovie(@PathVariable String userId) {
        FavoritesDTO favorites = userService.getFavorites(userId);
        List<Movie> favoriteMovies = favorites.getFavoriteMovies();

        if (favoriteMovies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No favorite movies available");
        }

        Movie randomMovie = favoriteMovies.get(new Random().nextInt(favoriteMovies.size()));
        return ResponseEntity.ok(randomMovie);
    }

    @GetMapping("/series/{userId}")
    public ResponseEntity<Object> randomFavoriteSeries(@PathVariable String userId) {
        FavoritesDTO favorites = userService.getFavorites(userId);
        List<Series> favoriteSeries = favorites.getFavoriteSeries();

        if (favoriteSeries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No favorite series available");
        }

        Series randomSeries = favoriteSeries.get(new Random().nextInt(favoriteSeries.size()));
        return ResponseEntity.ok(randomSeries);
    }
}
