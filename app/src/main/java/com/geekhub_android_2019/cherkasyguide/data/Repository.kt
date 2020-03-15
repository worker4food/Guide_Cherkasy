package com.geekhub_android_2019.cherkasyguide.data

import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Route
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository {

    private val rootRef by lazy {
        Firebase.firestore
    }

    fun getPlaces(): Flow<List<Place>> = rootRef.collection("places").asFlow()

    fun getRoutes(): Flow<List<Route>> = rootRef.collection("routes").asFlow()

    fun getUserRouteOrNUll(): Flow<Route?> = flowOf(null) // stub
}
