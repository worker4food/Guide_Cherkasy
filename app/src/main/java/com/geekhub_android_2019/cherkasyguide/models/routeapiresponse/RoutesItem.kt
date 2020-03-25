package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json
import java.io.Serializable

data class RoutesItem(@Json(name = "summary")
                      val summary: String?,
                      @Json(name = "copyrights")
                      val copyrights: String?,
                      @Json(name = "legs")
                      val legs: List<LegsItem?>?,
                      @Json(name = "bounds")
                      val bounds: Bounds?,
                      @Json(name = "overview_polyline")
                      val overviewPolyline: OverviewPolyline?,
                      @Json(name = "waypoint_order")
                      val waypointOrder: List<Int?>?): Serializable
