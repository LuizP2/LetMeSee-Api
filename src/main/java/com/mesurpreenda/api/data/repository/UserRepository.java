package com.mesurpreenda.api.data.repository;

import com.mesurpreenda.api.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByIdContainingIgnoreCase(String id);
}
