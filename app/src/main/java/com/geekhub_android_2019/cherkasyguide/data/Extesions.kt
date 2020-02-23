package com.geekhub_android_2019.cherkasyguide.data

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
inline fun <reified T: Any> Query.asFlow(): Flow<List<T>> = callbackFlow {
    val listener = addSnapshotListener { snapshot, ex ->
        if(ex != null) close(ex)
        else snapshot
            ?.toObjects<T>()
            ?.also { offer(it) }
    }

    awaitClose { listener.remove() }
}
