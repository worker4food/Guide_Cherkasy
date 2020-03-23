package com.geekhub_android_2019.cherkasyguide.ui.routemap

import android.util.Log
import androidx.lifecycle.ViewModel
import com.geekhub_android_2019.cherkasyguide.maputils.MapHelper
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.GoogleMap

class RouteViewModel : ViewModel() {

    private lateinit var placesForRoute: Places

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

    fun drawRoute() {
        val origin =
            placesForRoute[0].location?.latitude.toString() + "," + placesForRoute[0].location?.longitude.toString()
        Log.d("origin", origin)
        val destination =
            placesForRoute[placesForRoute.size - 1].location?.latitude.toString() + "," + placesForRoute[placesForRoute.size - 1].location?.longitude.toString()
        Log.d("destination", destination)
        val waypoints = buildWaypoints()
        Log.d("waypoints", waypoints)
        val mode = "driving"
        val key = "AIzaSyBpQLmolPgF0ObBK2SJPfSbnFS9ZcvoPbI"
        MapHelper.drawRoute(
            mMap,
            origin,
            destination,
            key,
            mode,
            waypoints
        )
    }

    private fun buildWaypoints(): String {
        val waypointsBuilder = StringBuilder("")
        if (placesForRoute.size > 2) {
            waypointsBuilder.append("optimize:true|")
            var i = 1
//            while (i < placesForRoute.size - 1) {
            while (i < 3) {
                waypointsBuilder
                    .append("via:")
                    .append(placesForRoute[i].location?.latitude)
                    .append(",")
                    .append(placesForRoute[i].location?.longitude)
                    .append("|")
                i++
            }
            waypointsBuilder
                .append("via:")
                .append(placesForRoute[6].location?.latitude)
                .append(",")
                .append(placesForRoute[6].location?.longitude)
        }
        Log.d("buildWaypoints", waypointsBuilder.toString())
        return waypointsBuilder.toString()
    }
}
