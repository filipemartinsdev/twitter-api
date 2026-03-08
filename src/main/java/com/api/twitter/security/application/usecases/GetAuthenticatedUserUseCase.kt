package com.api.twitter.security.application.usecases

import com.api.twitter.common.dto.AuthenticatedUser
import com.api.twitter.common.exception.UnauthorizedException
import com.api.twitter.security.domain.model.UserCredentials
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class GetAuthenticatedUserUseCase {
    fun execute(): AuthenticatedUser {
        try {
            val auth: Authentication = SecurityContextHolder.getContext().authentication
                ?: throw UnauthorizedException("User not authenticated")
            val userCredentials = auth.principal as UserCredentials

            return AuthenticatedUser(
                    userCredentials.userId,
                    userCredentials.username
            )
        } catch (e: Exception) {
            throw UnauthorizedException("User not authenticated")
        }
    }

    val id: UUID
        get() {
                val auth = SecurityContextHolder.getContext().authentication
                    ?: throw UnauthorizedException("User not authenticated")
                val userCredentials = auth.principal as UserCredentials
                return userCredentials.userId ?:
                    throw UnauthorizedException("Invalid user ID")
        }
}
