package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.*
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_routes_list.*

class RouteListFragment : Fragment(R.layout.fragment_routes_list) {

    private val vm by activityViewModels<RoutesViewModel>()
    private val controller: NavController
        get() = findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.routes.observe(viewLifecycleOwner, Observer {
            routeListSpinner.visibility = View.GONE

            it?.let(::assembleView)
        })

        vm.warn.observe(viewLifecycleOwner) {
            val msg = when (it) {
                Messages.ROUTE_TO_SHORT -> resources.getString(R.string.to_short_route)
                Messages.ROUTE_TO_LONG -> resources.getQuantityString(
                    R.plurals.to_long_route,
                    Limits.MAX_PLACES,
                    Limits.MAX_PLACES
                )
            }

            Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok) {}
                .show()
        }
    }

    private fun assembleView(state: ViewState) {
        routeList.withModels {
            val userRouteExists = state.userRoute?.places?.isNotEmpty() ?: false

            if (userRouteExists) {
                routeHeader {
                    id("user-route-header", state.userRoute!!.id)
                    name(getString(R.string.user_route))
                    listener { _ -> vm.viewRouteMap(controller, state.userRoute.places, getString(R.string.user_route)) }
                }

                carousel {
                    id("place-thumbs-user", state.userRoute!!.id)

                    state.userRoute.places.map { place ->
                        PlaceCardModel_()
                            .id("user-place-thumb", state.userRoute.id, place.id)
                            .title(place.name!!)
                            .imageUrl(place.photoSmallUrl!!)
                            .listener { _ -> vm.viewPlace(controller, place) }
                    }.also { models(it) }
                }
            }

            val buttonTextId =
                if (userRouteExists) R.string.edit_user_route else R.string.create_new_route

            createEditRoute {
                id("create-edit-user-route")
                titleId(buttonTextId)
                listener { _ -> vm.createEditRoute(controller) }
            }

            state.routes.forEach { route ->
                routeHeader {
                    id("route-header", route.id)
                    name(route.name!!)
                    listener { _ -> vm.viewRouteMap(controller, route.places, route.name) }
                }

                carousel {
                    id("place-thumbs", route.id)

                    route.places.map { place ->
                        PlaceCardModel_()
                            .id("place-thumb", route.id, place.id)
                            .title(place.name!!)
                            .imageUrl(place.photoSmallUrl!!)
                            .listener { _ -> vm.viewPlace(controller, place) }
                    }.also { models(it) }
                }
            }
        }
    }
}
