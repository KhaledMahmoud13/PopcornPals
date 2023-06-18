package com.khaled.popcornpals.domain.usecase.movie_use_case

import com.khaled.popcornpals.domain.model.Movie
import com.khaled.popcornpals.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetInTheatersMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> {
        return movieRepository.getInTheatersMovies()
    }
}