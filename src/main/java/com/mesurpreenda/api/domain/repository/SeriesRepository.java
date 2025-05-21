package com.mesurpreenda.api.domain.repository;

import com.mesurpreenda.api.data.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByTitleContainingIgnoreCase(String title);
}