package com.khaled.popcornpals.domain.model

data class Category(
    val id: Int,
    val movieCategory: String,
    val movies: List<Movie>
)
