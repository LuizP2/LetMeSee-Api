package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendationsDTO {
    private Integer page;
    private List<RecommendedContent> results;
    
    @JsonProperty("total_pages")
    private Integer totalPages;
    
    @JsonProperty("total_results")
    private Integer totalResults;

    @Getter
    @Setter
    public static class RecommendedContent {
        @JsonProperty("backdrop_path")
        private String backdropPath;
        
        private Long id;
        private String name;
        
        @JsonProperty("original_name")
        private String originalName;
        
        private String overview;
        
        @JsonProperty("poster_path")
        private String posterPath;
        
        @JsonProperty("media_type")
        private String mediaType;
        
        private Boolean adult;
        
        @JsonProperty("original_language")
        private String originalLanguage;
        
        @JsonProperty("genre_ids")
        private List<Integer> genreIds;
        
        private Double popularity;
        
        @JsonProperty("first_air_date")
        private String firstAirDate;
        
        @JsonProperty("vote_average")
        private Double voteAverage;
        
        @JsonProperty("vote_count")
        private Integer voteCount;
        
        @JsonProperty("origin_country")
        private List<String> originCountry;
        
        // Campos específicos para filmes
        private String title;
        
        @JsonProperty("original_title")
        private String originalTitle;
        
        @JsonProperty("release_date")
        private String releaseDate;
        
        private Boolean video;
    }
} 