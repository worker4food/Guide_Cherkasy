package com.geekhub_android_2019.cherkasyguide.ui.placemap

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geekhub_android_2019.cherkasyguide.maputils.MapHelper
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.GoogleMap

class PlaceViewModel : ViewModel() {

    private lateinit var _places: Places
    val places: Places get() = _places

    private lateinit var mMap: GoogleMap

    fun init(places: Places) {
        _places = places
    }

    fun createMap(googleMap: GoogleMap, context: Context) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = true
        MapHelper.clearMap(googleMap)
        val markersList = MapHelper.getMarkerList(_places)
        MapHelper.setUpClusterOfMarkers(mMap, markersList, context)
        MapHelper.setUpCamera(markersList, mMap)
    }
}
