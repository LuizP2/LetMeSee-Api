package com.mesurpreenda.api.data.service;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.repository.MovieRepository;
import com.mesurpreenda.api.data.repository.SeriesRepository;
import com.mesurpreenda.api.data.repository.UserRepository;
import com.mesurpreenda.api.domain.dto.FavoritesDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ApiServices {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private SeriesRepository seriesRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> updateUser(String id, User userDetails) {
        return userRepo.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return userRepo.save(user);
        });
    }

    public boolean deleteUser(String id) {
        return userRepo.findById(id).map(user -> {
            userRepo.delete(user);
            return true;
        }).orElse(false);
    }

    public FavoritesDTO getFavorites(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new FavoritesDTO(user.getFavoriteMovies(), user.getFavoriteSeries());
    }


    @Transactional
    public void addMovieToFavorites(String userId, Movie movie) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Movie savedMovie = movieRepo.save(movie);

        user.getFavoriteMovies().add(savedMovie);

        userRepo.save(user);
    }
    @Transactional
    public void addSeriesToFavorites(String userId, Series serie) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Series savedSeries = seriesRepo.save(serie);

        user.getFavoriteSeries().add(savedSeries);

        userRepo.save(user);
    }

    public void removeFavorite(String userId, Long contentId, boolean isMovie) {
        User user = userRepo.findById(userId).orElseThrow();
        if (isMovie) {
            user.getFavoriteMovies().removeIf(m -> m.getId().equals(contentId));
        } else {
            user.getFavoriteSeries().removeIf(s -> s.getId().equals(contentId));
        }
        userRepo.save(user);
    }
}