package com.geekhub_android_2019.cherkasyguide.ui.routemap

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.maputils.MapHelper
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.GoogleMap

class RouteViewModel : ViewModel() {

    private lateinit var placesForRoute: Places

    private val _typeOfRoute = MutableLiveData<String>("driving")
    val typeOfRoute: LiveData<String> = _typeOfRoute

    private lateinit var mMap: GoogleMap

    fun init(places: Places) {
        placesForRoute = places
    }

    fun createMap(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        MapHelper.clearMap(mMap)
        val markersList = MapHelper.getMarkerList(placesForRoute)
        MapHelper.setUpMarker(markersList, mMap)
        MapHelper.setUpCamera(markersList, mMap)
    }

    fun drawRoute(mode: String) {
        val origin =
            placesForRoute[0].location?.latitude.toString() + "," + placesForRoute[0].location?.longitude.toString()
        Log.d("origin", origin)
        val destination =
            placesForRoute[placesForRoute.size - 1].location?.latitude.toString() + "," + placesForRoute[placesForRoute.size - 1].location?.longitude.toString()
        Log.d("destination", destination)
        val waypoints = buildWaypoints()
        Log.d("waypoints", waypoints)
        MapHelper.drawRoute(
            mMap,
            origin,
            destination,
            mode,
            waypoints
        )
    }

    private fun buildWaypoints(): String {
        val waypointsBuilder = StringBuilder("")
        if (placesForRoute.size > 2) {
            waypointsBuilder.append("optimize:true|")
            var i = 1
            while (i < placesForRoute.size - 1) {
                waypointsBuilder
                    .append("via:")
                    .append(placesForRoute[i].location?.latitude)
                    .append(",")
                    .append(placesForRoute[i].location?.longitude)
                    .append("|")
                i++
            }
        }
        Log.d("buildWaypoints", waypointsBuilder.toString())
        return waypointsBuilder.toString()
    }

    fun selectTypeOfRoute(view: View) {
        when (view.id) {
            R.id.radio_button_car -> _typeOfRoute.value = "driving"
            R.id.radio_button_walking -> _typeOfRoute.value = "walking"
            R.id.radio_button_bus -> _typeOfRoute.value = "transit"
        }
    }
}
