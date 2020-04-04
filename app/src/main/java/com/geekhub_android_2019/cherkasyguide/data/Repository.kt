package com.geekhub_android_2019.cherkasyguide.data

import com.geekhub_android_2019.cherkasyguide.common.Collection
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.lang.IllegalArgumentException

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
                val routeFlows: List<Flow<Route>> = rawRoutes.map { rawRoute ->
                    fetchPlaces(rawRoute.placeIds)
                        .map { Route(rawRoute.id, rawRoute.name, it) }
                }

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

    suspend fun clearUserRoute(): Unit =
        updateUserRoute(UserRoute(FirebaseAuth.getInstance().uid!!, emptyList()))

    private fun fetchPlaces(placeIds_: List<String>): Flow<List<Place>> {
        val placeIds = placeIds_.take(Limits.MAX_PLACES)

        return if (placeIds.isNotEmpty())
            rootRef.collection(Collection.PLACES)
                .whereIn(FieldPath.documentId(), placeIds)
                .asFlow<Place>()
                .map { places ->
                    val id2place = places.associateBy { it.id }

                    placeIds.map { id ->
                        id2place[id]
                            ?: throw IllegalArgumentException("Place for id <$id> is null")
                    }
                }
        else
            flowOf(listOf())
    }
}
