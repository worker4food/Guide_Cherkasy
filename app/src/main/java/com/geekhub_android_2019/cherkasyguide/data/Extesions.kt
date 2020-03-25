package com.geekhub_android_2019.cherkasyguide.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

inline fun <reified T: Any> Query.asFlow(): Flow<List<T>> = callbackFlow {
    val listener = addSnapshotListener { snapshot, ex ->
        if(ex != null) close(ex)
        else snapshot
            ?.toObjects<T>()
            ?.also { offer(it) }
    }

    awaitClose { listener.remove() }
}

inline fun <reified T> DocumentReference.asFlow(): Flow<T> = asNullableFlow<T>().map { it!! }

inline fun <reified T> DocumentReference.asNullableFlow(): Flow<T?> = callbackFlow {
    val listener = addSnapshotListener { snapshot, ex ->
        when {
            ex != null -> close(ex)
            snapshot?.exists() ?: false -> snapshot?.toObject<T>().also { offer(it) }
            else -> offer(null)
        }
    }

    awaitClose { listener.remove() }
}
