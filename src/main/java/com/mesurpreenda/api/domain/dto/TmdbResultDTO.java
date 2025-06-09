package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class TmdbResultDTO {
    private String backdrop_path;
    private List<Integer> genre_ids;
    private Long id;
    private String overview;
    private String poster_path;
    @JsonAlias({ "title", "name" })
    private String title;
    @JsonAlias({"release_date", "first_air_date"})
    private String release_date;
}
