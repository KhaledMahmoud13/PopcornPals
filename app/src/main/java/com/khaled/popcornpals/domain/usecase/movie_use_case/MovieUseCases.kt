package com.khaled.popcornpals.domain.usecase.movie_use_case

data class MovieUseCases(
    val getInTheatersMoviesUseCase: GetInTheatersMoviesUseCase,
    val getTopMoviesUseCase: GetTopMoviesUseCase,
    val getMostPopularMoviesUseCase: GetMostPopularMoviesUseCase,
    val getComingSoonMoviesUseCase: GetComingSoonMoviesUseCase,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val searchUseCase: SearchUseCase
)
