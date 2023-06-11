package com.khaled.popcornpals.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val releaseDate: String,
    val runtimeStr: String,
    val plot: String,
    val awards: String,
    val directors: String,
    val writers: String,
    val stars: String,
    val actorList: List<Actor>,
    val genres: String,
    val companies: String,
    val countries: String,
    val languages: String,
    val contentRating: String,
    val imDbRating: String,
    val link: String,
) : Parcelable {
    constructor(
        id: String,
        title: String,
        fullTitle: String,
        year: String,
        image: String,
        imDbRating: String,
    ) : this(
        id,
        title,
        fullTitle,
        year,
        image,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        emptyList(),
        "",
        "",
        "",
        "",
        "",
        imDbRating,
        "",
    )
}