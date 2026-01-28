package com.api.twitter.user.infrastructure.persistence;

import com.api.twitter.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
     Optional<User> findByUsername(String username);

     Optional<User> findByEmail(String email);

     boolean existsByUsername(String username);

     boolean existsByEmail(String username);
}
