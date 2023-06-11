package com.khaled.popcornpals.data.remote

import com.khaled.popcornpals.data.model.Actor
import com.khaled.popcornpals.data.model.Movie
import org.json.JSONObject

fun parseMovies(jsonResult: JSONObject): List<Movie> {
    val items = jsonResult.getJSONArray("items")
    val movieList = mutableListOf<Movie>()
    for (i in 0 until items.length()) {
        val movieJson = items.getJSONObject(i)
        val id = movieJson.getString("id")
        val title = movieJson.getString("title")
        val fullTitle = movieJson.getString("fullTitle")
        val year = movieJson.getString("year")
        val image = movieJson.getString("image")
        val imDbRating = movieJson.getString("imDbRating")

        val movie = Movie(id, title, fullTitle, year, image, imDbRating)
        movieList.add(movie)
    }
    return movieList
}

fun parseMovieDetails(jsonResult: JSONObject): Movie {
    val id = jsonResult.getString("id")
    val title = jsonResult.getString("title")
    val fullTitle = jsonResult.getString("fullTitle")
    val year = jsonResult.getString("year")
    val image = jsonResult.getString("image")
    val releaseDate = jsonResult.getString("releaseDate")
    val runtimeStr = jsonResult.getString("runtimeStr")
    val plot = jsonResult.getString("plot")
    val awards = jsonResult.getString("awards")
    val directors = jsonResult.getString("directors")
    val writers = jsonResult.getString("writers")
    val stars = jsonResult.getString("stars")
    val actorList = jsonResult.getJSONArray("actorList")
    val actors = mutableListOf<Actor>()
    for (i in 0 until actorList.length()) {
        val actorJson = actorList.getJSONObject(i)
        val id = actorJson.getString("id")
        val image = actorJson.getString("image")
        val name = actorJson.getString("name")
        val asCharacter = actorJson.getString("asCharacter")
        val actor = Actor(id, image, name, asCharacter)
        actors.add(actor)
    }
    val genres = jsonResult.getString("genres")
    val companies = jsonResult.getString("companies")
    val countries = jsonResult.getString("countries")
    val languages = jsonResult.getString("languages")
    val contentRating = jsonResult.getString("contentRating")
    val imDbRating = jsonResult.getString("imDbRating")
    val trailer = jsonResult.getJSONObject("trailer")
    val link = trailer.getString("link")

    return Movie(
        id,
        title,
        fullTitle,
        year,
        image,
        releaseDate,
        runtimeStr,
        plot,
        awards,
        directors,
        writers,
        stars,
        actors,
        genres,
        companies,
        countries,
        languages,
        contentRating,
        imDbRating,
        link
    )
}