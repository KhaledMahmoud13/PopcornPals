package com.khaled.popcornpals.data.repository

import com.khaled.popcornpals.data.model.Movie
import com.khaled.popcornpals.data.remote.ApiService
import com.khaled.popcornpals.data.remote.parseMovieDetails
import com.khaled.popcornpals.data.remote.parseMovies
import com.khaled.popcornpals.data.remote.parseSearch
import com.khaled.popcornpals.domain.repository.MovieRepository
import org.json.JSONObject
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MovieRepository {
    override suspend fun getMostPopularMovies(): List<Movie> {
        return parseMovies(JSONObject(apiService.getMostPopularMovies()))
    }

    override suspend fun getTopMovies(): List<Movie> {
        return parseMovies(JSONObject(apiService.getTopMovies()))
    }

    override suspend fun getInTheatersMovies(): List<Movie> {
        return parseMovies(JSONObject(apiService.getInTheatersMovies()))
    }

    override suspend fun getComingSoonMovies(): List<Movie> {
        return parseMovies(JSONObject(apiService.getComingSoonMovies()))
    }

    override suspend fun getMovieDetails(id: String): Movie {
        return parseMovieDetails(JSONObject(apiService.getMovieDetails(id)))
    }

    override suspend fun searchTitle(expression: String): List<Movie> {
        return parseSearch(JSONObject(apiService.searchTitle(expression)))
    }
}