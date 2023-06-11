package com.khaled.popcornpals.di

import com.google.firebase.auth.FirebaseAuth
import com.khaled.popcornpals.data.remote.ApiService
import com.khaled.popcornpals.data.repository.AuthRepositoryImpl
import com.khaled.popcornpals.data.repository.MovieRepositoryImpl
import com.khaled.popcornpals.domain.repository.AuthRepository
import com.khaled.popcornpals.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }
}