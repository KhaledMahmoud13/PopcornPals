package com.khaled.popcornpals.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    val id: String,
    val image: String,
    val name: String,
    val asCharacter: String,
) : Parcelable
