package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json

data class GeocodedWaypointsItem(@Json(name = "types")
                                 val types: List<String?>?,
                                 @Json(name = "geocoder_status")
                                 val geocoderStatus: String?,
                                 @Json(name = "place_id")
                                 val placeId: String?)
