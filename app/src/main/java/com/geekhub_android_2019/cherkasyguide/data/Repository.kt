package com.geekhub_android_2019.cherkasyguide.data

import com.geekhub_android_2019.cherkasyguide.common.Collection
import com.geekhub_android_2019.cherkasyguide.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.security.InvalidParameterException

class Repository {

    private val rootRef by lazy {
        Firebase.firestore
    }

    fun getPlaceById(id: String): Flow<Place> =
        rootRef.collection(Collection.PLACES).document(id).asFlow()

    fun getPlaces(): Flow<List<Place>> = rootRef.collection(Collection.PLACES).asFlow()

    fun getRoutes(): Flow<List<Route>> =
        rootRef.collection(Collection.ROUTES)
            .asFlow<Internal.Route>()
            .transformLatest { rawRoutes ->
                // Emit routes without places
                emit(rawRoutes.map { Route(it.id, it.name) })

                // Construct routes with places
                val routeFlows: List<Flow<Route>> = rawRoutes.map { rawRoute ->
                    fetchPlaces(rawRoute.placeIds)
                        .map { Route(rawRoute.id, rawRoute.name, it) }
                }

                // Finally, emit routes with places
                combine(routeFlows) { it.asList() }
                    .also { emitAll(it) }
            }

    fun getUserRouteOrNUll(): Flow<UserRoute?> =
        rootRef.collection(Collection.USER_ROUTES)
            .document(FirebaseAuth.getInstance().uid!!)
            .asNullableFlow<Internal.UserRoute>()
            .flatMapLatest { rawRoute ->
                rawRoute
                    ?.let { fetchPlaces(rawRoute.placeIds) }
                    ?.map { UserRoute(rawRoute.id, it) }
                    ?: flowOf(null)
            }

    suspend fun updateUserRoute(r: UserRoute) {
        val uid = FirebaseAuth.getInstance().uid!!
        val placeRefs = r.places.map {
            rootRef.collection(Collection.USER_ROUTES).document(it.id!!)
        }

        rootRef.collection(Collection.USER_ROUTES)
            .document(uid)
            .set(Internal.UserRoute(r.id, placeRefs))
            .await()
    }

    private fun fetchPlaces(placeIds: List<String>): Flow<List<Place>> =
        if (placeIds.isNotEmpty())
            rootRef.collection(Collection.PLACES)
                .whereIn(FieldPath.documentId(), placeIds)
                .asFlow<Place>()
                .map { places ->
                    val id2place = places.associateBy { it.id }

                    placeIds.map { id ->
                        id2place[id]
                            ?: throw InvalidParameterException("Place for id <$id> is null")
                    }
                }
        else
            flowOf(listOf())
}
