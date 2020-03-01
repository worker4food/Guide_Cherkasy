package com.geekhub_android_2019.cherkasyguide.ui.placemap

import androidx.lifecycle.ViewModel
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PlaceViewModel : ViewModel() {

    private lateinit var _places: Places
    val places: Places get() = _places

    fun init(places: Places) {
        _places = places
    }

    private fun getPosition(place: Place): LatLng {
        val lat = place.location?.latitude ?: 49.444566
        val lng = place.location?.longitude ?: 32.059962
        return LatLng(lat, lng)
    }

    fun addMarker(googleMap: GoogleMap) {
        for (place in _places) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(getPosition(place))
                    .title(place.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.camera))
            )
        }
    }

    fun moveCamera(googleMap: GoogleMap) {
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                getPosition(_places[0]),
                15.0f
            )
        )
    }
}
