package com.geekhub_android_2019.cherkasyguide.ui.placemap

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geekhub_android_2019.cherkasyguide.maputils.MapHelper
import com.geekhub_android_2019.cherkasyguide.maputils.Utils
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.GoogleMap

class PlaceViewModel : ViewModel() {

    private lateinit var _places: Places

    private lateinit var mMap: GoogleMap

    fun init(places: Places) {
        _places = places
    }

    fun createMap(googleMap: GoogleMap, context: Context) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = true
        MapHelper.clearMap(googleMap)
        val markersList = Utils.getMarkerList(_places)
        MapHelper.setUpClusterOfMarkers(mMap, markersList, context)
        val updateCamera =
            if (markersList.toList().size == 1) {
                MapHelper.updateCameraZoom(markersList)
            } else {
                MapHelper.updateCameraBounds(markersList)
            }
        MapHelper.setUpCamera(mMap, updateCamera)
    }
}
