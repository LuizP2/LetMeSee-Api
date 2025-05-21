package com.mesurpreenda.api.domain.dao;

import com.mesurpreenda.api.data.entity.Movie;
import com.mesurpreenda.api.data.entity.Series;
import com.mesurpreenda.api.domain.repository.SeriesRepository;
import com.mesurpreenda.api.util.RandomSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class SeriesDAO extends BaseDAO<Series> {
    @Autowired
    public SeriesDAO(SeriesRepository seriesRepository) {
        this.repository = seriesRepository;
    }

    public List<Series> search(String title) {
        return ((SeriesRepository) repository).findByTitleContainingIgnoreCase(title);
    }
    public Optional<Series> findRandom(){
        return RandomSelector.getRandom(repository.findAll());
    }
}
