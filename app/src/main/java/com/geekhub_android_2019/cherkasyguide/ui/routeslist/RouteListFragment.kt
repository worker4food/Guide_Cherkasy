package com.geekhub_android_2019.cherkasyguide.ui.routeslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.ui.routeslist.models.RouteItem
import com.geekhub_android_2019.cherkasyguide.ui.routeslist.models.RoutesViewModel
import kotlinx.android.synthetic.main.fragment_routes_list.*

class RouteListFragment : Fragment(R.layout.fragment_routes_list) {

    private val vm by activityViewModels<RoutesViewModel>()
    private val routesAdapter by lazy { RouteListAdapter(::onRouteSelected) }
    private val controller: NavController
        get() = findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        routeList.adapter = routesAdapter

        vm.routes.observe(viewLifecycleOwner, Observer {
            routesAdapter.submitList(it)
        })
    }

    private fun onRouteSelected(item: RouteItem): Unit = when(item) {
        is RouteItem.Regular -> vm.viewRouteMap(controller, item.route)
        is RouteItem.User -> vm.viewRouteMap(controller, item.route)
        is RouteItem.CreateNew -> vm.createNewRoute(controller)
        is RouteItem.Separator -> Unit // Do nothing
    }
}
