package com.khaled.popcornpals.presentation.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.khaled.popcornpals.domain.usecase.auth_use_case.AuthUseCases
import com.khaled.popcornpals.util.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun loginUser(
        email: String,
        password: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        authUseCases.loginUseCase(email, password, onComplete, onFail)
    }

    fun isEmailValid(email: String?): Boolean {
        return (!email.isNullOrEmpty() && email.isValidEmail())
    }

    fun isPasswordValid(password: String?): Boolean {
        return !password.isNullOrEmpty() && password.length > 6
    }

    fun registerUser(
        email: String,
        password: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        authUseCases.registerUseCase(email, password, onComplete, onFail)
    }

    fun logout(result: () -> Unit) {
        authUseCases.logoutUseCase (result)
    }
}