package com.geekhub_android_2019.cherkasyguide.ui.placemap

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.ui.routemap.RouteViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_map.*

class PlaceMapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val args: PlaceMapFragmentArgs by navArgs()
    private val  placeViewModel: PlaceViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        placeViewModel.init(args.places)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        placeViewModel.createMap(googleMap, activity!!.applicationContext)
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
