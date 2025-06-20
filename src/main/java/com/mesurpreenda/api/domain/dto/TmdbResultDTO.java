package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TmdbResultDTO {
    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("backdrop_path")
    private String backdropPath;
    
    @JsonProperty("genres")
    private List<TmdbGenreDTO> genres;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("overview")
    private String overview;
       
    @JsonProperty("poster_path")
    private String posterPath;
    
    @JsonAlias({"title", "name"})
    private String title;
    
    @JsonAlias({"release_date", "first_air_date"})
    private String releaseDate;
}
