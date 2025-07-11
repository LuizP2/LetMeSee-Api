package com.mesurpreenda.api.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "movie")
@Getter
@Setter
@Entity
public class Movie {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String title;
    private String genre;
    private String year;
}

