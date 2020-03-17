package com.geekhub_android_2019.cherkasyguide

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem

class MyMarker : ClusterItem {
    private val mPosition: LatLng
    private val mTitle: String
    private val mSnippet: String

    private lateinit var marker: MarkerOptions

    constructor(lat: Double, lng: Double) {
        mPosition = LatLng(lat, lng)
        mTitle = ""
        mSnippet = ""
    }

    constructor(lat: Double, lng: Double, title: String) {
        mPosition = LatLng(lat, lng)
        mTitle = title
        mSnippet = ""
    }

    override fun getPosition(): LatLng {
        return mPosition
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getSnippet(): String {
        return mSnippet
    }

    fun setMarker (markerOptions: MarkerOptions) {
        marker = markerOptions
    }
}
