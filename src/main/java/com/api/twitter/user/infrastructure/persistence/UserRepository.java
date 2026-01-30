package com.api.twitter.user.infrastructure.persistence;

import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
     Optional<User> findByUsername(String username);

     Optional<User> findByEmail(String email);

     boolean existsByUsername(String username);

     boolean existsByEmail(String username);

     Page<User> findAllByUsernameContainingIgnoreCase(String query, Pageable pageable);
}
