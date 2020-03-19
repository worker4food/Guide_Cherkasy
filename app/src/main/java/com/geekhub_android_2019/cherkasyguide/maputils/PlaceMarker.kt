package com.geekhub_android_2019.cherkasyguide.maputils

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem

class PlaceMarker(lat: Double, lng: Double, title: String) : ClusterItem {
    private val mPosition: LatLng = LatLng(lat, lng)
    private val mTitle: String = title
    private val mSnippet: String = ""

    override fun getPosition(): LatLng {
        return mPosition
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSnippet(): String {
        return mSnippet
    }

}
