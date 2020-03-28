package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RouteEditViewModel: ViewModel() {

    private val repo = Repository()

    val state: LiveData<ViewState> =
        combine(repo.getPlaces(), repo.getUserRouteOrNUll()) { places, userRoute ->
            ViewState(places, userRoute)
        }.asLiveData()

    fun toggleCheck(place: Place) = viewModelScope.launch {
        val (id, selected) = state.value?.userRoute ?: UserRoute()

        val newPlaces =
            if(selected.contains(place)) selected - place
            else selected + place

        UserRoute(id, newPlaces).also { repo.updateUserRoute(it) }
    }
}
