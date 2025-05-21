package com.mesurpreenda.api.data.service;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import com.mesurpreenda.api.domain.repository.MovieRepository;
import com.mesurpreenda.api.domain.repository.SeriesRepository;
import com.mesurpreenda.api.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private SeriesRepository seriesRepo;

    public FavoritesDTO getFavorites(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new FavoritesDTO(user.getFavoriteMovies(), user.getFavoriteSeries());
    }


    public void addFavorite(Long userId, Long contentId, boolean isMovie) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (isMovie) {
            Movie movie = movieRepo.findById(contentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
            user.getFavoriteMovies().add(movie);
        } else {
            Series series = seriesRepo.findById(contentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found"));
            user.getFavoriteSeries().add(series);
        }
        userRepo.save(user);
    }

    public void removeFavorite(Long userId, Long contentId, boolean isMovie) {
        User user = userRepo.findById(userId).orElseThrow();
        if (isMovie) {
            user.getFavoriteMovies().removeIf(m -> m.getId().equals(contentId));
        } else {
            user.getFavoriteSeries().removeIf(s -> s.getId().equals(contentId));
        }
        userRepo.save(user);
    }
}