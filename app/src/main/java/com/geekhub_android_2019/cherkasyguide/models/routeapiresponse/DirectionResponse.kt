package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json
import java.io.Serializable

data class DirectionResponse(@Json(name = "routes")
                             val routes: List<RoutesItem?>?,
                             @Json(name = "geocoded_waypoints")
                             val geocodedWaypoints: List<GeocodedWaypointsItem>?,
                             @Json(name = "status")
                             val status: String?): Serializable
