package com.mesurpreenda.api.domain.dto;

import lombok.Data;

@Data
public class MovieDTO {
    private Long id;     // Pode ser nulo se quiser que o banco gere o ID.
    private String title;
    private String genre;
    private int year;
}