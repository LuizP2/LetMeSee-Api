package com.mesurpreenda.api.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.mesurpreenda.api.domain.model.Season;
import java.util.List;
import java.util.ArrayList;

@Table(name = "series")
@Getter
@Setter
@Entity
public class Series {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    private String title;
    
    @ElementCollection
    @CollectionTable(
        name = "series_genres",
        joinColumns = @JoinColumn(name = "series_id")
    )
    @Column(name = "genre_id")
    private List<Integer> genre = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(
        name = "series_seasons",
        joinColumns = @JoinColumn(name = "series_id")
    )
    private List<Season> seasons = new ArrayList<>();
}