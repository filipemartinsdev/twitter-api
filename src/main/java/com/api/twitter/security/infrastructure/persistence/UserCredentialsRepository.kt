package com.api.twitter.security.infrastructure.persistence

import com.api.twitter.security.domain.model.UserCredentials
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import java.util.Optional
import java.util.UUID

interface UserCredentialsRepository : JpaRepository<UserCredentials, UUID> {
    fun findByUsername(username: String): Optional<UserDetails>

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean
}
