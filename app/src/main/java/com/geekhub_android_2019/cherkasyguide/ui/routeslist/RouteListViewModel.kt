package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import androidx.lifecycle.*
import androidx.navigation.NavController
import com.geekhub_android_2019.cherkasyguide.common.AppDispatchers
import com.geekhub_android_2019.cherkasyguide.common.EventChannel
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.geekhub_android_2019.cherkasyguide.network.NetHelper
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class RouteListViewModel(
    private val repo: Repository,
    private val netHelper: NetHelper,
    private val dispatchers: AppDispatchers) : ViewModel() {

    val warn = EventChannel<Messages>()

    val routes: LiveData<ViewState> by lazy {
        val routesF = repo.getRoutes().conflate()
        val userRouteF = repo.getUserRouteOrNUll().conflate()

        routesF
            .combine(userRouteF) { routes, userRoute ->
                ViewState(routes, userRoute)
            }
            .flowOn(dispatchers.io)
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun createEditRoute(navController: NavController) {
        if (netHelper.isOnline) RouteListFragmentDirections.actionToRouteEditFragment()
            .also { navController.navigate(it) }
        else
            warn.offer(Messages.NO_NETWORK)
    }

    fun viewRouteMap(navController: NavController, places: List<Place>, routeName: String) {
        when {
            !netHelper.isOnline -> warn.offer(Messages.NO_NETWORK)
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
        if (netHelper.isOnline) RouteListFragmentDirections.actionToPlaceDetail(place, place.name)
            .also { navController.navigate(it) }
        else
            warn.offer(Messages.NO_NETWORK)
    }
}

