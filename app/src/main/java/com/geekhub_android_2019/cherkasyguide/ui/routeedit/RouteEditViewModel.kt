package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import androidx.lifecycle.*
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.common.EventChannel
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RouteEditViewModel : ViewModel() {

    private val repo: Repository = Repository()

    val warn = EventChannel<Messages>()

    val state: LiveData<ViewState> by lazy {
        val placesF = repo.getPlaces().conflate()
        val userRouteF = repo.getUserRouteOrNUll().conflate()

        placesF
            .combine(userRouteF) { places, userRoute ->
                ViewState(places, userRoute)
            }
            .flowOn(Dispatchers.IO)
            .conflate()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun toggleCheck(place: Place) = viewModelScope.launch(Dispatchers.IO) {
        val (id, selected) = state.value?.userRoute ?: UserRoute()

        val newPlaces =
            if (selected.contains(place)) selected - place
            else selected + place

        if (newPlaces.size > Limits.MAX_PLACES)
            warn.offer(Messages.ROUTE_TO_LONG)
        else
            UserRoute(id, newPlaces)
                .also { repo.updateUserRoute(it) }
    }

    fun clearUserRoute() = viewModelScope.launch {
        repo.clearUserRoute()
    }
}
