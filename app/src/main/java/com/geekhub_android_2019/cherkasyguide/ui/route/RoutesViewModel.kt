package com.geekhub_android_2019.cherkasyguide.ui.route

import android.util.Log
import androidx.lifecycle.*
import com.geekhub_android_2019.cherkasyguide.data.*
import com.geekhub_android_2019.cherkasyguide.models.*

class RoutesViewModel: ViewModel() {

    private val repo = Repository()

    val routes = repo.getRoutes().asLiveData()

    fun onRouteSelected(r: Route) {
        Log.d("WAT", "$r")
    }
}
