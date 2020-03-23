package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json
import java.io.Serializable

data class LegsItem(@Json(name = "duration")
                    val duration: Duration?,
                    @Json(name = "start_location")
                    val startLocation: StartLocation?,
                    @Json(name = "distance")
                    val distance: Distance?,
                    @Json(name = "start_address")
                    val startAddress: String?,
                    @Json(name = "end_location")
                    val endLocation: EndLocation?,
                    @Json(name = "end_address")
                    val endAddress: String?,
                    @Json(name = "steps")
                    val steps: List<StepsItem?>?): Serializable
