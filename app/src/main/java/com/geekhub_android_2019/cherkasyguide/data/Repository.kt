package com.geekhub_android_2019.cherkasyguide.data

import com.geekhub_android_2019.cherkasyguide.models.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*

class Repository {

    private val rootRef by lazy {
        Firebase.firestore
    }

    fun getPlaceById(id: String): Flow<Place> =
        rootRef.collection("places").document(id).asFlow()

    fun getPlaces(): Flow<List<Place>> = rootRef.collection("places").asFlow()

    fun getRoutes(): Flow<List<Route>> =
        rootRef.collection("routes")
            .asFlow<Internal.Route>()
            .transformLatest { rawRoutes ->
                // Emit routes without places
                emit(rawRoutes.map { Route(it.id, it.name) })

                // Construct routes with places
                val routesFlows: List<Flow<Route>> = rawRoutes.map { rawRoute ->
                    fetchRoutePlaces(rawRoute)
                }

                // Finally, emit routes with places
                combine(routesFlows) { newRoutes: Array<Route> ->
                    newRoutes.toList()
                }.also {
                    emitAll(it)
                }
            }

    fun getUserRouteOrNUll(): Flow<Route?> = flowOf(null) // stub

    private fun fetchPlaces(places: List<DocumentReference>): Flow<List<Place>> =
        if (places.isNotEmpty())
            rootRef.collection("places")
                .whereIn(FieldPath.documentId(), places.map { it.id })
                .asFlow()
        else
            flowOf(listOf())

    private fun fetchRoutePlaces(r: Internal.Route): Flow<Route> =
        fetchPlaces(r.places).map { Route(r.id, r.name, it) }
}
