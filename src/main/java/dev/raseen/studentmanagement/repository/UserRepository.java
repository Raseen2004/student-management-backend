package dev.raseen.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.raseen.studentmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
}
