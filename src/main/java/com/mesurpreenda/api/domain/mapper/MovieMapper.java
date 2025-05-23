package com.mesurpreenda.api.domain.mapper;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.domain.dto.MovieDTO;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setGenre(dto.getGenre());
        movie.setYear(dto.getYear());
        return movie;
    }

    public MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(Long.valueOf(movie.getId()));
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setYear(movie.getYear());
        return dto;
    }
}