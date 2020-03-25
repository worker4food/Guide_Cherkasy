package com.geekhub_android_2019.cherkasyguide.models

import com.google.firebase.firestore.*
import java.io.Serializable

data class Route(
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    val places: List<Place> = listOf()
): Serializable
