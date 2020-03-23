package com.geekhub_android_2019.cherkasyguide.ui.routemap

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.geekhub_android_2019.cherkasyguide.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_map.*

class RouteMapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    private val args: RouteMapFragmentArgs by navArgs()
    private val routeViewModel: RouteViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        routeViewModel.init(args.places)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        routeViewModel.createMap(googleMap)
        routeViewModel.drawRoute()
    }

    override fun onResume() {
        map_view.onResume()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map_view.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view.onLowMemory()
    }
}
