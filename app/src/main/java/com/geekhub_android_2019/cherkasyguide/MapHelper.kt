package com.geekhub_android_2019.cherkasyguide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager

object MapHelper {

    private lateinit var builder: LatLngBounds.Builder

    private fun getPosition(place: Place): LatLng {
        val lat = place.location?.latitude ?: 49.444566
        val lng = place.location?.longitude ?: 32.059962
        return LatLng(lat, lng)
    }

    fun setUpMap(googleMap: GoogleMap, places: Places, context: Context) {
        googleMap.clear()
        val markerList: ArrayList<MyMarker> = ArrayList()
        val clusterManager = ClusterManager<MyMarker>(context, googleMap)
        for (place in places) {
            val mItem = MyMarker(getPosition(place).latitude, getPosition(place).longitude, place.name!!)
//            mItem.setMarker(
//                MarkerOptions()
//                    .position(getPosition(place))
//                    .title(place.name)
//                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMarkerIcon(context)))
//            )
            markerList.add(mItem)
        }
        clusterManager.addItems(markerList)
        builder = LatLngBounds.builder()
        for (marker in markerList) {
            builder.include(marker.position)
        }
        val bounds = builder.build()
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 50)
        googleMap.moveCamera(cu)
        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)
    }

    private fun resizeMarkerIcon(context: Context): Bitmap {
        val b = BitmapFactory.decodeResource(context.resources, R.drawable.pin_blue)
        return Bitmap.createScaledBitmap(b, 100, 100, false)
    }
}
