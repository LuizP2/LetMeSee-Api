package com.mesurpreenda.api.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Season {
    private Integer episodeCount;
    private Long id;
    private String overview;
    private String posterPath;
    private Integer seasonNumber;
    private String releaseDate;
} 