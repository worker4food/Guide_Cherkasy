package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json

data class Polyline(@Json(name = "points")
                    val points: String?)
