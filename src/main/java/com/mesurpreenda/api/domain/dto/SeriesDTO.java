package com.mesurpreenda.api.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SeriesDTO {
    private Long id;     // Pode ser nulo se quiser que o banco gere o ID.
    private String title;
    private String genre;
    private Integer seasons;
}