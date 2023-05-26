package com.khaled.popcornpals.network

import com.khaled.popcornpals.model.Movie
import org.json.JSONObject

fun parseMovie(jsonResult: JSONObject): ArrayList<Movie> {
    val items = jsonResult.getJSONArray("items")
    val movieList = ArrayList<Movie>()
    for (i in 0 until items.length()) {
        val movieJson = items.getJSONObject(i)
        val id = movieJson.getString("id")
        val rank = movieJson.getString("rank")
        val title = movieJson.getString("title")
        val fullTitle = movieJson.getString("fullTitle")
        val year = movieJson.getString("year")
        val image = movieJson.getString("image")
        val crew = movieJson.getString("crew")
        val imDbRating = movieJson.getString("imDbRating")
        val imDbRatingCount = movieJson.getString("imDbRatingCount")

        val movie =
            Movie(id, rank, title, fullTitle, year, image, crew, imDbRating, imDbRatingCount)
        movieList.add(movie)
    }
    return movieList
}