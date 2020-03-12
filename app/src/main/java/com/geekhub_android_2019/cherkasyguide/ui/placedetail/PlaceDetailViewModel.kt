package com.geekhub_android_2019.cherkasyguide.ui.placedetail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places

class PlaceDetailViewModel : ViewModel() {

    private val repo = Repository()

    val placedetail: LiveData<List<Place>> =
        repo.getPlaces().asLiveData()

    fun replaceDetailFragment(view: View) {
        val placeDetail = Places().apply { addAll(placedetail.value!!) }
        PlaceDetailFragmentDirections.actionPlaceDetailFragmentToPlaceMapFragment(placeDetail)
            .also {
                view.findNavController().navigate(it)
            }
    }
}
