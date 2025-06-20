package com.mesurpreenda.api.domain.dto;

import com.mesurpreenda.api.domain.model.Season;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SeriesDTO {
    private Long id;
    private String title;
    private List<Integer> genre;
    private List<Season> seasons;
}