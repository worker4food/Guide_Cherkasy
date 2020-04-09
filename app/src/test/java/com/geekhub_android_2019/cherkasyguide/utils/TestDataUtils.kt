package com.geekhub_android_2019.cherkasyguide.utils

import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Route
import com.geekhub_android_2019.cherkasyguide.models.UserRoute

fun mkPlaces(count: Int) = (1..count)
    .map {
        Place(
            id = "place_$it",
            name = "name for place #$it",
            description = "description for place #$it"
        )
    }

fun mkPlace() = mkPlaces(1).first()

fun mkRoutes(count: Int, placesInRoute: Int) = (1..count)
    .map {
        Route(
            id = "route_$it",
            name = "route name $it",
            places = mkPlaces(placesInRoute)
        )
    }

fun mkRoute(placesInRoute: Int) = mkRoutes(1, placesInRoute).first()

fun mkUserRoute(placesInRoute: Int) =
    UserRoute("user_route", mkPlaces(placesInRoute))
