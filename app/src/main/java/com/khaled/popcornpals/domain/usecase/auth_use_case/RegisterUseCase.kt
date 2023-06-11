package com.khaled.popcornpals.domain.usecase.auth_use_case

import com.khaled.popcornpals.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(
        email: String,
        password: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        return authRepository.registerUser(email, password, onComplete, onFail)
    }
}