package com.geekhub_android_2019.cherkasyguide.ui.startScreen

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.geekhub_android_2019.cherkasyguide.R

class StartScreenViewModel : ViewModel() {

    fun select (view: View) {
        when (view.id) {
            R.id.cardView_what_to_see ->
                view.findNavController().navigate(R.id.action_startScreenFragment_to_placesListFragment)
            R.id.cardView_routes ->
                view.findNavController().navigate(R.id.action_startScreenFragment_to_routeFragment)
        }
    }
}
