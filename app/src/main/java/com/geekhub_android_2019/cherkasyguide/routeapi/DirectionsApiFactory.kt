package com.geekhub_android_2019.cherkasyguide.routeapi

import android.content.Context
import com.geekhub_android_2019.cherkasyguide.BuildConfig
import com.geekhub_android_2019.cherkasyguide.models.routeapiresponse.DirectionResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object DirectionsApiFactory {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
//        .addInterceptor(AuthInterceptor())
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val directionsApiService = retrofit.create(DirectionsApiService::class.java)

    fun getDirectionsApiService(
        origin: String,
        destination: String,
        key: String,
        mode: String,
        waypoints: String
    ): Call<DirectionResponse> {
        return directionsApiService.getDirection(origin, destination, key, mode, waypoints)
    }

}
