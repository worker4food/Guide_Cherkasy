package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import androidx.lifecycle.*
import androidx.navigation.NavController
import com.geekhub_android_2019.cherkasyguide.common.EventChannel
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class RoutesViewModel : ViewModel() {

    private val repo = Repository()
    val warn = EventChannel<Messages>()

    val routes: LiveData<ViewState> =
        combine(repo.getRoutes(), repo.getUserRouteOrNUll()) { routes, userRoute ->
            ViewState(
                routes,
                userRoute
            )
        }
        .flowOn(Dispatchers.IO)
        .conflate()
        .asLiveData(viewModelScope.coroutineContext)

    fun createEditRoute(navController: NavController) {
        RouteListFragmentDirections.actionToRouteEditFragment().also {
            navController.navigate(it)
        }
    }

    fun viewRouteMap(navController: NavController, places: List<Place>, routeName: String) {
        when {
            places.size < 2 -> warn.offer(Messages.ROUTE_TO_SHORT)
            places.size > Limits.MAX_PLACES -> warn.offer(Messages.ROUTE_TO_LONG)
            else -> {
                val arg = Places().apply { addAll(places) }
                RouteListFragmentDirections.actionToRouteMap(arg, routeName).also {
                    navController.navigate(it)
                }
            }
        }
    }

    fun viewPlace(navController: NavController, place: Place) {
        RouteListFragmentDirections.actionToPlaceDetail(place, place.name).also {
            navController.navigate(it)
        }
    }
}

