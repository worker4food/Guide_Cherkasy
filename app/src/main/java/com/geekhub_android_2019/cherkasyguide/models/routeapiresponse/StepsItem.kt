package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json
import java.io.Serializable

data class StepsItem(@Json(name = "duration")
                     val duration: Duration?,
                     @Json(name = "start_location")
                     val startLocation: StartLocation?,
                     @Json(name = "travel_mode")
                     val travelMode: String?,
                     @Json(name = "distance")
                     val distance: Distance?,
                     @Json(name = "html_instructions")
                     val htmlInstructions: String?,
                     @Json(name = "end_location")
                     val endLocation: EndLocation?,
                     @Json(name = "polyline")
                     val polyline: Polyline?): Serializable
