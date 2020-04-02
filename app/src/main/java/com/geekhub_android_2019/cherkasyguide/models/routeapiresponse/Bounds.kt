package com.geekhub_android_2019.cherkasyguide.models.routeapiresponse

import com.squareup.moshi.Json
import java.io.Serializable

data class Bounds(@Json(name = "southwest")
                  val southwest: Southwest?,
                  @Json(name = "northeast")
                  val northeast: Northeast?): Serializable
