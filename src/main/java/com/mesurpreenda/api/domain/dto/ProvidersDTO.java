package com.mesurpreenda.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProvidersDTO {
    private Long id;
    private Map<String, CountryProviders> results;

    @Getter
    @Setter
    public static class CountryProviders {
        private String link;
        
        @JsonProperty("buy")
        private List<Provider> buyProviders;
        
        @JsonProperty("flatrate")
        private List<Provider> flatrateProviders;
        
        @JsonProperty("rent")
        private List<Provider> rentProviders;
    }

    @Getter
    @Setter
    public static class Provider {
        @JsonProperty("logo_path")
        private String logoPath;
        
        @JsonProperty("provider_id")
        private Integer providerId;
        
        @JsonProperty("provider_name")
        private String providerName;
        
        @JsonProperty("display_priority")
        private Integer displayPriority;
    }
} 