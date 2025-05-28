package com.mesurpreenda.api.domain.dto;

import lombok.Data;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private int year;
}