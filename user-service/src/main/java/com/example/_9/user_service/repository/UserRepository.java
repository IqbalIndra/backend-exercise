package com.example._9.user_service.repository;

import com.example._9.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByNameIgnoreCase(String name);
}
