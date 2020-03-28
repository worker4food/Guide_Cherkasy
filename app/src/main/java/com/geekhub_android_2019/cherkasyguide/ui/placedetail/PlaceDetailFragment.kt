package com.geekhub_android_2019.cherkasyguide.ui.placedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.geekhub_android_2019.cherkasyguide.R
import com.google.android.material.navigation.NavigationView
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import kotlinx.android.synthetic.main.fragment_place_detail.view.*


class PlaceDetailFragment : Fragment() {

    val placeArgs by navArgs<PlaceDetailFragmentArgs>()
    private val detailViewModel by activityViewModels<PlaceDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_place_detail , container , false)
        val navController = NavHostFragment.findNavController(this)
        val bottomBar = view?.findViewById<NavigationView>(R.id.bottom_nav_view)
        bottomBar?.setupWithNavController(navController)
        fragmentLayout.bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map_nav -> {
                    detailViewModel.replaceDetailFragment(this.requireView() , placeArgs.place)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        return fragmentLayout
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter =
            placeArgs.place.photoLargeUrl?.let { it1 -> PlaceDetailPagerAdapter(photos = it1) }
        view.textView_description?.text = placeArgs.place.description
        view.textView_name?.text = placeArgs.place.name
        val springDotsIndicator = view.findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator)
        springDotsIndicator.setViewPager(viewPager)
    }
}
