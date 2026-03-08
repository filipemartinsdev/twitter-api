package com.api.twitter.security.application.usecases

import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class DeleteUserCredentialsUseCase(private val userCredentialsRepository: UserCredentialsRepository) {
    fun execute(userId: UUID) {
        userCredentialsRepository.deleteById(userId)
    }
}
