package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json

data class Duration(@Json(name = "text")
                    val text: String?,
                    @Json(name = "value")
                    val value: Int?)
