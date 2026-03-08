package com.api.twitter.security.application.usecases

import com.api.twitter.common.exception.BadRequestException
import com.api.twitter.security.domain.model.UserCredentials
import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Supplier
import kotlin.jvm.optionals.getOrNull

@Component
class UpdateUserCredentialsUseCase(
    private val passwordEncoder: PasswordEncoder,
    private val userCredentialsRepository: UserCredentialsRepository
) {

    fun execute(userId: UUID, username: String, email: String) {
        val userCredentials = userCredentialsRepository.findById(userId).getOrNull()
            ?: throw BadRequestException("User not found")

        userCredentials.rawUsername = username
        userCredentials.email = email
        userCredentials.validate()
        userCredentialsRepository.save(userCredentials)
    }

    fun updatePassword(userId: UUID, password: String) {
        val userCredentials = userCredentialsRepository.findById(userId).getOrNull()
            ?: throw RuntimeException("User not found")

        userCredentials.rawPassword = password
        userCredentials.validatePassword()
        userCredentials.rawPassword = this.passwordEncoder.encode(password)
            ?: throw BadRequestException("Invalid password")

        userCredentialsRepository.save(userCredentials)
    }
}
