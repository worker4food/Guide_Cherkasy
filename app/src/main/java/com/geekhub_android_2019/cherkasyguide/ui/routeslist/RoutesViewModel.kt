package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.geekhub_android_2019.cherkasyguide.models.Route
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import kotlinx.coroutines.flow.combine

class RoutesViewModel : ViewModel() {

    private val repo = Repository()

    val routes: LiveData<ViewState> =
        combine(repo.getRoutes(), repo.getUserRouteOrNUll()) { routes, userRoute ->
            ViewState(
                routes,
                userRoute
            )
        }.asLiveData()

    fun createEditRoute(navController: NavController) {
        RouteListFragmentDirections.actionToRouteEditFragment().also {
            navController.navigate(it)
        }
    }

    fun viewRouteMap(navController: NavController, places: List<Place>) {
        val arg = Places().apply { addAll(places) }
        RouteListFragmentDirections.actionToRouteMap(arg).also {
            navController.navigate(it)
        }
    }

    fun viewPlace(navController: NavController, place: Place) {
        RouteListFragmentDirections.actionToPlaceDetail(place, place.name).also {
            navController.navigate(it)
        }
    }
}

