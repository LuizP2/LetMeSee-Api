package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TmdbResponseDTO<T> {
    private int page;
    private List<T> results;
    
    @JsonProperty("total_pages")
    private int totalPages;
    
    @JsonProperty("total_results")
    private int totalResults;
}