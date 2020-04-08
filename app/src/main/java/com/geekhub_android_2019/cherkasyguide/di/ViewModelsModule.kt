package com.geekhub_android_2019.cherkasyguide.di

import com.geekhub_android_2019.cherkasyguide.ui.placeslist.PlacesListViewModel
import com.geekhub_android_2019.cherkasyguide.ui.routeedit.RouteEditViewModel
import com.geekhub_android_2019.cherkasyguide.ui.routeslist.RouteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { RouteEditViewModel(get(), get()) }
    viewModel { RouteListViewModel(get(), get(), get()) }
    viewModel { PlacesListViewModel(get(), get()) }
}
