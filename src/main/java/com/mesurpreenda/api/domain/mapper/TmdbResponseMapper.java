package com.mesurpreenda.api.domain.mapper;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.domain.dto.MovieDTO;
import com.mesurpreenda.api.domain.dto.SeriesDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class TmdbResponseMapper {
    public Movie toMovieEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setGenre(dto.getGenre());
        movie.setYear(dto.getYear());
        return movie;
    }
    public MovieDTO toMovieDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(Long.valueOf(movie.getId()));
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setYear(movie.getYear());
        return dto;
    }
    public Series toSeriesEntity(SeriesDTO dto) {
        Series series = new Series();
        series.setTitle(dto.getTitle());
        series.setGenre(dto.getGenre());
        series.setSeasons(dto.getSeasons());
        return series;
    }
    public SeriesDTO toSeriesDTO(Series series) {
        SeriesDTO dto = new SeriesDTO();
        dto.setId(Long.valueOf(series.getId()));
        dto.setTitle(series.getTitle());
        dto.setGenre(series.getGenre());
        dto.setSeasons(series.getSeasons());
        return dto;
    }
}