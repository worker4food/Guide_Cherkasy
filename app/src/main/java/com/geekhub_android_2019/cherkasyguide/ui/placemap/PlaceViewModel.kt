package com.geekhub_android_2019.cherkasyguide.ui.placemap

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geekhub_android_2019.cherkasyguide.MapHelper
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.GoogleMap

class PlaceViewModel : ViewModel() {

    private lateinit var _places: Places
    val places: Places get() = _places

    fun init(places: Places) {
        _places = places
    }

    fun createMap(googleMap: GoogleMap, context: Context) =
        MapHelper.setUpMap(googleMap, _places, context)
}
