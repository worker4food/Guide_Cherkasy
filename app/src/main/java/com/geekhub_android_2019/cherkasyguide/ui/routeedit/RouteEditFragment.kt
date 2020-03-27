package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.common.verticalGridCarousel
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import kotlinx.android.synthetic.main.fragment_routeedit.*

class RouteEditFragment : Fragment(R.layout.fragment_routeedit) {

    private val vm by activityViewModels<RouteEditViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.state.observe(viewLifecycleOwner, Observer {
            it?.let { (places, userRoute) ->
                assembleViewModel(places, userRoute ?: UserRoute())
            }
        })
    }

    private fun assembleViewModel(places: List<Place>, userRoute: UserRoute) {
        val selected = userRoute.places.toSet()

        placesList.withModels {
            verticalGridCarousel {
                id("user-route-places", userRoute.id)

                places.map { place ->
                    PlaceItemModel_()
                        .id("place-item", place.id)
                        .title(place.name!!)
                        .imageUrl(place.photoSmallUrl!!)
                        .checked(selected.contains(place))
                        .listener { _ -> vm.toggleCheck(place) }
                }.also { models(it) }
            }
        }
    }
}
