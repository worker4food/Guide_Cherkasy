package com.geekhub_android_2019.cherkasyguide.data

import com.geekhub_android_2019.cherkasyguide.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await

class Repository {

    private val rootRef by lazy {
        Firebase.firestore
    }

    fun getPlaces(): Flow<List<Place>> = rootRef.collection("places").asFlow()

    fun getRoutes(): Flow<List<Route>> = rootRef.collection("routes").asFlow()

    fun getUserRouteOrNUll(): Flow<Route?> = flowOf(null) // stub
}
