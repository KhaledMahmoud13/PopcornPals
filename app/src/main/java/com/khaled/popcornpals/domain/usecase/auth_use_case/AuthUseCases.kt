package com.khaled.popcornpals.domain.usecase.auth_use_case

data class AuthUseCases(
    val registerUseCase: RegisterUseCase,
    val loginUseCase: LoginUseCase,
    val forgotPasswordUseCase: ForgotPasswordUseCase,
    val logoutUseCase: LogoutUseCase
)
