package com.api.twitter.security.application.usecases

import com.api.twitter.common.events.UserCreatedEvent
import com.api.twitter.common.exception.BadRequestException
import com.api.twitter.common.model.UserRole
import com.api.twitter.security.domain.model.UserCredentials
import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RegisterUserCredentialsUseCase(
    private val userCredentialsRepository: UserCredentialsRepository,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun execute(username: String, email: String, password: String) {
        verifyIfUsernameOrEmailIsAlreadyInUse(username, email)

        val userCredentials = UserCredentials(
            email = email,
            rawPassword = password,
            rawUsername = username,
            role = UserRole.USER,
            createdAt = LocalDateTime.now(),
        ).apply {
            this.validate()
        }.also {
            it.rawPassword = passwordEncoder.encode(password)
                ?: throw RuntimeException("Invalid encrypted password")
        }
        val user = userCredentialsRepository.save<UserCredentials>(encodePassword(userCredentials))
        publishUserCreatedEvent(user)
    }

    private fun verifyIfUsernameOrEmailIsAlreadyInUse(username: String, email: String) {
        if (userCredentialsRepository.existsByUsername(username))
            throw BadRequestException("This username is already in use")
        if (userCredentialsRepository.existsByEmail(email))
            throw BadRequestException("This email is already in use")
    }

    private fun encodePassword(userCredentials: UserCredentials): UserCredentials {
        userCredentials.rawPassword = passwordEncoder.encode(userCredentials.rawPassword)
            ?: throw RuntimeException("Invalid encrypted password")
        return userCredentials
    }

    private fun publishUserCreatedEvent(user: UserCredentials) {
        applicationEventPublisher.publishEvent(
            UserCreatedEvent(
                userId = user.userId ?: throw BadRequestException("User id is null"),
                username = user.rawUsername,
                email = user.email,
                objSource = this
            )
        )
    }
}
