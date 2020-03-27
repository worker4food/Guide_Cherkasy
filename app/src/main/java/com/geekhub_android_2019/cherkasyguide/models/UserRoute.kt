package com.geekhub_android_2019.cherkasyguide.models

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class UserRoute(
    @DocumentId
    val id: String? = null,
    val places: List<Place> = listOf()
): Serializable
