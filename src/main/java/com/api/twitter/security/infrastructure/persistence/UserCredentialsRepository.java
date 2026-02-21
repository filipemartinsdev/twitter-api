package com.api.twitter.security.infrastructure.persistence;

import com.api.twitter.security.domain.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID> {
    Optional<UserDetails> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
