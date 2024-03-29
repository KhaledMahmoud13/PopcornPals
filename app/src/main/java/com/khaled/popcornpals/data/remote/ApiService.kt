package com.khaled.popcornpals.data.remote

import com.khaled.popcornpals.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("MostPopularMovies/$API_KEY")
    suspend fun getMostPopularMovies(): String

    @GET("Top250Movies/$API_KEY")
    suspend fun getTopMovies(): String

    @GET("InTheaters/$API_KEY")
    suspend fun getInTheatersMovies(): String

    @GET("ComingSoon/$API_KEY")
    suspend fun getComingSoonMovies(): String

    @GET("BoxOffice/$API_KEY")
    suspend fun getBoxOfficeMovies(): String

    @GET("BoxOfficeAllTime/$API_KEY")
    suspend fun getBoxOfficeAllTimeMovies(): String

    @GET("Title/$API_KEY/{Id}/Trailer")
    suspend fun getMovieDetails(@Path("Id") id: String): String

    @GET("Search/$API_KEY/{Expression}")
    suspend fun searchTitle(@Path("Expression") expression: String): String

}