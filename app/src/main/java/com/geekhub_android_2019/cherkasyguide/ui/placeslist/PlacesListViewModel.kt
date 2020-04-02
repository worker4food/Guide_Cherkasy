package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class PlacesListViewModel : ViewModel() {

    private val repo = Repository()

    val places: LiveData<List<Place>> = repo.getPlaces()
        .flowOn(Dispatchers.IO)
        .conflate()
        .asLiveData(viewModelScope.coroutineContext)

    fun list(view: View, place: Place) {
        PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailFragment(
            place,
            place.name
        ).also {
            view.findNavController().navigate(it)
        }
    }

    fun replaceFragment(view: View) {
        val places = Places().apply { addAll(places.value!!) }
        PlacesListFragmentDirections.actionPlacesListFragmentToPlaceMapFragment(places).also {
            view.findNavController().navigate(it)
        }
    }
}
