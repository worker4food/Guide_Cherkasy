package com.geekhub_android_2019.cherkasyguide.routeapi

import android.content.Context
import com.geekhub_android_2019.cherkasyguide.R
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().url(
                chain.request().url.newBuilder()
                    .addQueryParameter("appid", context.getString(R.string.google_maps_key))
                    .build()
            ).build()
        )
    }
}
