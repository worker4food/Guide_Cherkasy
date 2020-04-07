package com.geekhub_android_2019.cherkasyguide.testData

import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Route
import com.geekhub_android_2019.cherkasyguide.models.UserRoute

fun placesGen(count: Int) = (1..count)
    .map {
        Place(
            id = "place_$it",
            name = "name for place #$it",
            description = "description for place #$it"
        )
    }

fun placeGen() = placesGen(1).first()

fun routesGen(count: Int, placesInRoute: Int) = (1..count)
    .map {
        Route(
            id = "route_$it",
            name = "route name $it",
            places = placesGen(placesInRoute)
        )
    }

fun routeGen(placesInRoute: Int) = routesGen(1, placesInRoute).first()

fun userRoute(placesInRoute: Int) =
    UserRoute("user_route", placesGen(placesInRoute))
