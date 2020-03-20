package com.geekhub_android_2019.cherkasyguide.ui.placedetail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places

class PlaceDetailViewModel : ViewModel() {

    lateinit var place: Place

    fun replaceDetailFragment(
        view: View,
        place: Place
    ) {
        val placeDetail = Places().apply { add(place) }
        PlaceDetailFragmentDirections.actionPlaceDetailFragmentToPlaceMapFragment(placeDetail)
            .also {
                view.findNavController().navigate(it)
            }
    }
}
