package com.khaled.popcornpals.util

sealed class Result {
    data class Success(var data: Any? = null, var message: String? = null) : Result()
    data class Error(var message: String) : Result()
    object Loading : Result()
}