package com.geekhub_android_2019.cherkasyguide.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

internal class Internal {
    data class Route(
        @DocumentId
        val id: String? = null,
        val name: String? = null,
        val places: List<DocumentReference> = listOf()
    )
}
