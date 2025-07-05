package com.mesurpreenda.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingContentDTO {
    
    private Long id;
    private String title;
    private String overview;
    private String posterPath;
    private String type; // "MOVIE" ou "SERIES"
    private Double voteAverage;
    private String releaseDate;
    private String[] genres;
} 