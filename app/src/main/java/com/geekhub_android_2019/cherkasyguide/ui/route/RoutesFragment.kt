package com.geekhub_android_2019.cherkasyguide.ui.route

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Route
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.fragment_routes_list.*

class RoutesFragment : Fragment() {

    private val vm by activityViewModels<RoutesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_routes_list, container, false)
            .let { it as RecyclerView }
            .apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = RoutesAdapter(::onRouteSelected)

            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.routes.observe(viewLifecycleOwner, Observer {
            (list.adapter as ListAdapter<Route, RoutesAdapter.ViewHolder>).submitList(it)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun onRouteSelected(r: Route) {
        findNavController().navigate(R.id.action_routesFragment_to_routeFragment)
    }
}
