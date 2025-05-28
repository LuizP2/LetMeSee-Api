package com.mesurpreenda.api.domain.dto;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FavoritesDTO {
    private List<Movie> favoriteMovies;
    private List<Series> favoriteSeries;

    public FavoritesDTO(List<Movie> favoriteMovies, List<Series> favoriteSeries) {
        this.favoriteMovies = favoriteMovies;
        this.favoriteSeries = favoriteSeries;
    }
}
