package com.mesurpreenda.api.domain.dto;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;

import java.util.List;

public class FavoritesDTO {
    private List<Movie> favoriteMovies;
    private List<Series> favoriteSeries;

    public FavoritesDTO(List<Movie> favoriteMovies, List<Series> favoriteSeries) {
        this.favoriteMovies = favoriteMovies;
        this.favoriteSeries = favoriteSeries;
    }

    public List<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(List<Movie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public List<Series> getFavoriteSeries() {
        return favoriteSeries;
    }

    public void setFavoriteSeries(List<Series> favoriteSeries) {
        this.favoriteSeries = favoriteSeries;
    }
}
