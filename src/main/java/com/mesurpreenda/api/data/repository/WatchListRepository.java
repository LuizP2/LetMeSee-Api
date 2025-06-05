package com.mesurpreenda.api.data.repository;

import com.mesurpreenda.api.data.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, String> {
    // Aqui poderíamos adicionar consultas personalizadas, se necessário.
}
