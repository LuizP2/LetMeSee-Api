package com.mesurpreenda.api.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private String name;
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_favorites_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @JsonIgnore
    private final List<Movie> favoriteMovies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_favorites_series",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "series_id")
    )
    @JsonIgnore
    private final List<Series> favoriteSeries = new ArrayList<>();


}
