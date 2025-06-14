package com.mesurpreenda.api.data.service;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.data.entity.User;
import com.mesurpreenda.api.data.entity.WatchList;
import com.mesurpreenda.api.data.repository.MovieRepository;
import com.mesurpreenda.api.data.repository.SeriesRepository;
import com.mesurpreenda.api.data.repository.UserRepository;
import com.mesurpreenda.api.data.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private SeriesRepository seriesRepo;

    @Autowired
    private UserRepository userRepo;

    public WatchList createWatchList(String title, String creatorUserId) {
        User creator = userRepo.findById(creatorUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        WatchList watchList = new WatchList();
        watchList.setTitle(title);
        watchList.getCollaborators().add(creator);
        return watchListRepo.save(watchList);
    }

    public List<WatchList> getAllWatchLists() {
        return watchListRepo.findAll();
    }

    public Optional<WatchList> getWatchListById(String id) {
        return watchListRepo.findById(id);
    }

    public Optional<WatchList> updateWatchListTitle(String id, String newTitle) {
        return watchListRepo.findById(id).map(wl -> {
            wl.setTitle(newTitle);
            return watchListRepo.save(wl);
        });
    }

    public boolean deleteWatchList(String id) {
        return watchListRepo.findById(id).map(wl -> {
            watchListRepo.delete(wl);
            return true;
        }).orElse(false);
    }

    @Transactional
    public void addCollaborator(String watchListId, String userId) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        wl.getCollaborators().add(user);
        watchListRepo.save(wl);
    }

    @Transactional
    public void removeCollaborator(String watchListId, String userId) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));
        wl.getCollaborators().removeIf(u -> u.getId().equals(userId));
        watchListRepo.save(wl);
    }

    @Transactional
    public void addMovieToWatchList(String watchListId, Movie movie) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));
        Movie savedMovie = movieRepo.findById(String.valueOf(movie.getId()))
                .orElseGet(() -> movieRepo.save(movie));
        wl.getFavoriteMovies().add(savedMovie);
        watchListRepo.save(wl);
    }

    @Transactional
    public void removeMovieFromWatchList(String watchListId, Long movieId) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));
        wl.getFavoriteMovies().removeIf(m -> m.getId().equals(movieId));
        watchListRepo.save(wl);
    }

    @Transactional
    public void addSeriesToWatchList(String watchListId, Series series) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));
        Series savedSeries = seriesRepo.findById(String.valueOf(series.getId()))
                .orElseGet(() -> seriesRepo.save(series));
        wl.getFavoriteSeries().add(savedSeries);
        watchListRepo.save(wl);
    }

    @Transactional
    public void removeSeriesFromWatchList(String watchListId, Long seriesId) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));
        wl.getFavoriteSeries().removeIf(s -> s.getId().equals(seriesId));
        watchListRepo.save(wl);
    }

    public Object getRandomFromWatchList(String watchListId) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));

        List<Object> combined = new ArrayList<>();
        combined.addAll(wl.getFavoriteMovies());
        combined.addAll(wl.getFavoriteSeries());

        if (combined.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No content in watchlist");

        return combined.get(new Random().nextInt(combined.size()));
    }

    public Movie getRandomMovieFromWatchList(String watchListId) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));

        List<Movie> movies = wl.getFavoriteMovies();
        if (movies.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies in watchlist");

        return movies.get(new Random().nextInt(movies.size()));
    }

    public Series getRandomSeriesFromWatchList(String watchListId) {
        WatchList wl = watchListRepo.findById(watchListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WatchList not found"));

        List<Series> series = wl.getFavoriteSeries();
        if (series.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No series in watchlist");

        return series.get(new Random().nextInt(series.size()));
    }
}
