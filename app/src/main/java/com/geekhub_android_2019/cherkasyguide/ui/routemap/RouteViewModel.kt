package com.geekhub_android_2019.cherkasyguide.ui.routemap

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.maputils.MapHelper
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.geekhub_android_2019.cherkasyguide.routeapi.OnDrawRouteFailure
import com.google.android.gms.maps.GoogleMap

class RouteViewModel : ViewModel(), OnDrawRouteFailure {

    private lateinit var placesForRoute: Places

    private val _typeOfRoute = MutableLiveData<String>("driving")
    val typeOfRoute: LiveData<String> = _typeOfRoute

    private val _statusDrawButton = MutableLiveData<Boolean>(true)
    val statusDrawButton: LiveData<Boolean> = _statusDrawButton

    private lateinit var mMap: GoogleMap

    init {
        MapHelper.drawRouteFailureCallback = this
    }

    fun init(places: Places) {
        placesForRoute = places
    }

    fun createMap(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.uiSettings.isZoomControlsEnabled = true
        MapHelper.clearMap(mMap)
        val markersList = MapHelper.getMarkerList(placesForRoute)
        MapHelper.setUpMarker(markersList, mMap)
        MapHelper.setUpCamera(markersList, mMap)
    }

    fun drawRoute(mode: String) {
        val originLocation = placesForRoute[0].location
        val destanitionLocation = placesForRoute[placesForRoute.size - 1].location
        val origin =
            originLocation?.latitude.toString() + "," + originLocation?.longitude.toString()
        val destination =
            destanitionLocation?.latitude.toString() + "," + destanitionLocation?.longitude.toString()
        val waypoints = buildWaypoints()
        MapHelper.drawRoute(
            mMap,
            origin,
            destination,
            mode,
            waypoints
        )
    }

    override fun drawRouteFailure() {
        Log.d("Error", "drawRouteFailure")
        _statusDrawButton.value = false
    }

    private fun buildWaypoints(): String {
        val waypointsBuilder = StringBuilder("")
        if (placesForRoute.size > 2) {
            var startElement = 1
            val lastElement = placesForRoute.size - 1
            while (startElement < lastElement) {
                val waypointLocation = placesForRoute[startElement].location
                waypointsBuilder
                    .append(waypointLocation?.latitude)
                    .append(",")
                    .append(waypointLocation?.longitude)
                    .append("|")
                startElement++
            }
        }
        Log.d("buildWaypoints", waypointsBuilder.toString())
        return waypointsBuilder.toString()
    }

    fun selectTypeOfRoute(view: View) {
        when (view.id) {
            R.id.radio_button_car ->
                _typeOfRoute.value = "driving"

            R.id.radio_button_walking ->
                _typeOfRoute.value = "walking"

        }
    }

    fun buttonStartClick(count: Int, view: View) {
        val size = placesForRoute.size
        if (count < size - 1) {
            MapHelper.drawStepPolyline(mMap, count)
            val places: Places = Places()
            places.add(placesForRoute[count])
            places.add(placesForRoute[count + 1])
            val markers = MapHelper.getMarkerList(places)
            MapHelper.setUpCamera(markers, mMap)

            if (count == size - 2) {
                view.isEnabled = false
            }
        }
    }
}
