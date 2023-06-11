package com.khaled.popcornpals.domain.usecase.auth_use_case

import com.khaled.popcornpals.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(result: () -> Unit) {
        return authRepository.logout(result)
    }
}