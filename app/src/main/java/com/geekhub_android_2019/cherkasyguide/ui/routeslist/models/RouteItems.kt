package com.geekhub_android_2019.cherkasyguide.ui.routeslist.models

import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Route

sealed class RouteItem(val resourceId: Int) {
    data class Regular(val route: Route) : RouteItem(R.layout.fragment_routes_list_item)
    data class User(val route: Route) : RouteItem(R.layout.fragment_routes_list_item)
    data class Separator(val textResourceId: Int) : RouteItem(R.layout.fragment_routes_group_separator)
    object CreateNew : RouteItem(R.layout.fragment_routes_create_new)

    val id: String?
        get() = when(this) {
            is Regular -> route.id
            is User -> route.id
            is Separator -> "$this"
            else -> "${this::class.java}"
    }
}
