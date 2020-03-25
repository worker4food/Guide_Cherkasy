package com.geekhub_android_2019.cherkasyguide

import android.app.Application
import com.geekhub_android_2019.cherkasyguide.routeapi.DirectionsApiFactory

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        DirectionsApiFactory.init(this)
    }
}
