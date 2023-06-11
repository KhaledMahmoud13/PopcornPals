package com.khaled.popcornpals.domain.repository

interface AuthRepository {
    fun registerUser(email: String, password: String, onComplete: () -> Unit, onFail: (message: String) -> Unit)
    fun loginUser(email: String, password: String, onComplete: () -> Unit, onFail: (message: String) -> Unit)
    fun forgotPassword(email: String, onComplete: () -> Unit, onFail: (message: String) -> Unit)
    fun logout(result: () -> Unit)
}