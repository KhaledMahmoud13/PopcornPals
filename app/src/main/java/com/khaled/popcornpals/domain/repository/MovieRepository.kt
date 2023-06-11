package com.khaled.popcornpals.domain.repository

import com.khaled.popcornpals.data.model.Movie

interface MovieRepository {
    suspend fun getMostPopularMovies(): List<Movie>
    suspend fun getTopMovies(): List<Movie>
    suspend fun getInTheatersMovies(): List<Movie>
    suspend fun getComingSoonMovies(): List<Movie>
    suspend fun getMovieDetails(id: String): Movie
}