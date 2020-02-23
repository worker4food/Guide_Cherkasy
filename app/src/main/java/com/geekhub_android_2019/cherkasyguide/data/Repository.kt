package com.geekhub_android_2019.cherkasyguide.data

import com.geekhub_android_2019.cherkasyguide.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

class Repository {

    private val rootRef by lazy {
        //TODO: this may not work, fix this
        FirebaseAuth.getInstance()
            .signInAnonymously()

        Firebase.firestore
    }

    fun getPlaces() = rootRef.collection("places").asFlow<Place>()

    fun getRoutes() = rootRef.collection("routes").asFlow<Route>()
}
