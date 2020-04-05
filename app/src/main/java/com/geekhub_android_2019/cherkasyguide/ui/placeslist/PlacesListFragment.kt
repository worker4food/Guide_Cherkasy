package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekhub_android_2019.cherkasyguide.network.NetHelper
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_places_list.*
import kotlinx.android.synthetic.main.fragment_places_list.view.*


class PlacesListFragment : Fragment(), PlacesAdapter.OnItemClickListener {

    private lateinit var mAdapter: PlacesAdapter
    private val listViewModel by activityViewModels<PlacesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_places_list, container, false)
        val progressBar = fragmentLayout.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = ProgressBar.VISIBLE
        val navController = NavHostFragment.findNavController(this)
        val bottomBar = view?.findViewById<NavigationView>(R.id.bottom_nav_view)
        bottomBar?.setupWithNavController(navController)
        fragmentLayout.bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map_nav -> {
                    listViewModel.replaceFragment(this.requireView())
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        return fragmentLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        listViewModel.places.observe(viewLifecycleOwner, Observer<List<Place>> {
            mAdapter = PlacesAdapter(listViewModel.places.value!!, clickListener = this)
            recyclerView.apply {
                adapter = mAdapter
                visibility = View.VISIBLE
            }
            mAdapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        })
    }

    override fun onClick(place: Place) {
        if (NetHelper.getInstance(application = activity!!.application).isOnline) {
            listViewModel.list(this.requireView(), place)
        }
        val snackbar =
            Snackbar.make(
                requireView(),
                R.string.error_no_network,
                Snackbar.LENGTH_LONG
            )
        snackbar.anchorView = bottom_nav_view
        snackbar.setAction(android.R.string.ok) {}
        snackbar.show()
    }
}
