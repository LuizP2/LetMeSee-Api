package com.mesurpreenda.api.data.repository;

import com.mesurpreenda.api.data.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, String> {
    List<Series> findByTitleContainingIgnoreCase(String title);
}