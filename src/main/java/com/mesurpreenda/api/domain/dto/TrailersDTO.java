package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrailersDTO {
    private Long id;
    private List<TrailerResult> results;

    @Getter
    @Setter
    public static class TrailerResult {
        @JsonProperty("iso_639_1")
        private String languageCode;
        
        @JsonProperty("iso_3166_1")
        private String countryCode;
        
        @JsonProperty("name")
        private String name;
        
        @JsonProperty("key")
        private String key;
        
        @JsonProperty("site")
        private String site;
        
        @JsonProperty("published_at")
        private String publishedAt;
    }
} 