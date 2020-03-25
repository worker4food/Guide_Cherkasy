package com.geekhub_android_2019.cherkasyguide.routeapi

import com.geekhub_android_2019.cherkasyguide.models.routeapiresponse.DirectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApiService {
    @GET("maps/api/directions/json")
    fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String,
        @Query("waypoints") waypoints: String
    ): Call<DirectionResponse>
}
