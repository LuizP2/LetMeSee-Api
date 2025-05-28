package com.mesurpreenda.api.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public abstract class BaseDAO<T> {
    protected JpaRepository<T, String> repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findRandom() {
        List<T> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all.get(new Random().nextInt(all.size())));
    }
}

