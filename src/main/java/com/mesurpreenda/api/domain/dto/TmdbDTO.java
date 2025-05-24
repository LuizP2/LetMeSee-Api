package com.mesurpreenda.api.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class TmdbDTO {
    private String backdrop_path;
    private List<Integer> genre_ids;
    private Long id;
    private String overview;
    private String poster_path;
    private String title;
    private String release_date;
}
