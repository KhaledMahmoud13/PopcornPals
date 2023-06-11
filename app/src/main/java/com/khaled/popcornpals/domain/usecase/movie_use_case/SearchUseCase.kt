package com.khaled.popcornpals.domain.usecase.movie_use_case

import com.khaled.popcornpals.data.model.Movie
import com.khaled.popcornpals.domain.repository.MovieRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(expression: String): List<Movie> {
        return movieRepository.searchTitle(expression)
    }
}