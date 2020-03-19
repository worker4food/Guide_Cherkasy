package com.geekhub_android_2019.cherkasyguide.maputils

import android.content.Context
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm

object MapHelper {

    private lateinit var builder: LatLngBounds.Builder

    private fun getPosition(place: Place): LatLng {
        val lat = place.location?.latitude ?: 49.444566
        val lng = place.location?.longitude ?: 32.059962
        return LatLng(lat, lng)
    }

    fun setUpMap(googleMap: GoogleMap, places: Places, context: Context) {
        clearMap(googleMap)
        val placeMarkerList = getMarkerList(places)
        setUpClusterOfMarkers(
            googleMap,
            placeMarkerList,
            context
        )
        val cu = CameraUpdateFactory.newLatLngBounds(
            setUpBounds(placeMarkerList), 50
        )
        googleMap.moveCamera(cu)
    }

    private fun clearMap(googleMap: GoogleMap) = googleMap.clear()

    private fun setUpClusterOfMarkers(
        googleMap: GoogleMap,
        markerList: ArrayList<PlaceMarker>,
        context: Context
    ) {
        val manager = ClusterManager<PlaceMarker>(context, googleMap)
        manager.renderer = ClusterMarkerRenderer(context, googleMap, manager)
        manager.algorithm = GridBasedAlgorithm()
        manager.addItems(markerList)
        manager.cluster()
    }

    private fun setUpBounds(markerList: ArrayList<PlaceMarker>): LatLngBounds {
        builder = LatLngBounds.builder()
        for (marker in markerList) {
            builder.include(marker.position)
        }
        return builder.build()
    }

    private fun getMarkerList(places: Places): ArrayList<PlaceMarker> {
        val placeMarkerList = ArrayList<PlaceMarker>()
        for (place in places) {
            val mItem =
                PlaceMarker(
                    getPosition(place).latitude,
                    getPosition(place).longitude,
                    place.name!!
                )
            placeMarkerList.add(mItem)
        }
        return placeMarkerList
    }
}
