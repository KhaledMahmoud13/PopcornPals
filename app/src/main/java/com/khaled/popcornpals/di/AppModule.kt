package com.khaled.popcornpals.di

import com.khaled.popcornpals.domain.repository.AuthRepository
import com.khaled.popcornpals.domain.repository.MovieRepository
import com.khaled.popcornpals.domain.usecase.auth_use_case.AuthUseCases
import com.khaled.popcornpals.domain.usecase.auth_use_case.ForgotPasswordUseCase
import com.khaled.popcornpals.domain.usecase.auth_use_case.LoginUseCase
import com.khaled.popcornpals.domain.usecase.auth_use_case.LogoutUseCase
import com.khaled.popcornpals.domain.usecase.auth_use_case.RegisterUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetComingSoonMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetInTheatersMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetMostPopularMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetMovieDetailsUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.GetTopMoviesUseCase
import com.khaled.popcornpals.domain.usecase.movie_use_case.MovieUseCases
import com.khaled.popcornpals.domain.usecase.movie_use_case.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieUseCase(movieRepository: MovieRepository): MovieUseCases {
        return MovieUseCases(
            getInTheatersMoviesUseCase = GetInTheatersMoviesUseCase(movieRepository),
            getTopMoviesUseCase = GetTopMoviesUseCase(movieRepository),
            getMostPopularMoviesUseCase = GetMostPopularMoviesUseCase(movieRepository),
            getComingSoonMoviesUseCase = GetComingSoonMoviesUseCase(movieRepository),
            getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository),
            searchUseCase = SearchUseCase(movieRepository)
        )
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            registerUseCase = RegisterUseCase(authRepository),
            loginUseCase = LoginUseCase(authRepository),
            forgotPasswordUseCase = ForgotPasswordUseCase(authRepository),
            logoutUseCase = LogoutUseCase(authRepository)
        )
    }

}