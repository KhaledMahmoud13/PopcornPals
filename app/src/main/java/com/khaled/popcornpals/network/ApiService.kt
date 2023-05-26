package com.khaled.popcornpals.network

import com.khaled.popcornpals.util.Constants.API_KEY
import com.khaled.popcornpals.util.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiService {
    @GET("Top250Movies/$API_KEY")
    suspend fun getTopMovies(): String

    @GET("MostPopularMovies/$API_KEY")
    suspend fun getMostPopularMovies(): String
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}