package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.common.BaseViewModel
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RouteEditViewModel : BaseViewModel<Messages>() {

    private val repo = Repository()

    val state: LiveData<ViewState> =
        combine(repo.getPlaces(), repo.getUserRouteOrNUll()) { places, userRoute ->
            ViewState(places, userRoute)
        }.asLiveData()

    fun toggleCheck(place: Place) = viewModelScope.launch {
        val (id, selected) = state.value?.userRoute ?: UserRoute()

        val newPlaces =
            if (selected.contains(place)) selected - place
            else selected + place

        if (newPlaces.size > Limits.MAX_PLACES)
            warn(Messages.ROUTE_TO_LONG)
        else
            UserRoute(id, newPlaces)
                .also { repo.updateUserRoute(it) }
    }
}
