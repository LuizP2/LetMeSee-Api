package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TmdbGenreDTO {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
} 