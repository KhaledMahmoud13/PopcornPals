package com.khaled.popcornpals.data.model

data class Category(
    val id: Int,
    val movieCategory: String,
    val movies: List<Movie>
)
