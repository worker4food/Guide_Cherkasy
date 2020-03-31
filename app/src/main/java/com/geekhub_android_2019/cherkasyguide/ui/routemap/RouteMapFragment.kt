package com.geekhub_android_2019.cherkasyguide.ui.routemap

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.geekhub_android_2019.cherkasyguide.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.fragment_map.map_view
import kotlinx.android.synthetic.main.fragment_map_route.*

class RouteMapFragment : Fragment(R.layout.fragment_map_route), OnMapReadyCallback,
    View.OnClickListener {

    private val args: RouteMapFragmentArgs by navArgs()
    private val routeViewModel: RouteViewModel by activityViewModels()
    var mCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)

        steps_of_route.visibility = GONE
        textView_route_title.text = args.routeTitle

        radio_button_walking.setOnClickListener(this)
        radio_button_car.setOnClickListener(this)
        button_start_route.setOnClickListener {
            steps_of_route.visibility = VISIBLE
            button_start_route.text = getString(R.string.next)
            routeViewModel.buttonStartClick(mCount, it)
            radio_button_car.isEnabled = false
            radio_button_walking.isEnabled = false
            mCount++
        }

        routeViewModel.startPlace.observe(viewLifecycleOwner, Observer {
            textView_start_point.text = it.name
        })

        routeViewModel.endPlace.observe(viewLifecycleOwner, Observer {
            textView_end_point.text = it.name
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        routeViewModel.init(args.places)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        routeViewModel.createMap(googleMap)
        routeViewModel.typeOfRoute.observe(this, Observer {
            routeViewModel.drawRoute(it)
        })

        routeViewModel.statusDrawButton.observe(this, Observer {
            button_start_route.isEnabled = it
        })

        listOf(radio_button_car, radio_button_walking).forEach {
            it.isChecked = it.id == routeViewModel.lastRadioState
        }
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

    override fun onClick(view: View) {
        routeViewModel.selectTypeOfRoute(view)
    }
}
