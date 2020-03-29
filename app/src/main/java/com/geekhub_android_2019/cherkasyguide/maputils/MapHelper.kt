package com.geekhub_android_2019.cherkasyguide.maputils

import android.content.Context
import android.graphics.*
import android.util.Log
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.geekhub_android_2019.cherkasyguide.models.routeapiresponse.DirectionResponse
import com.geekhub_android_2019.cherkasyguide.models.routeapiresponse.RoutesItem
import com.geekhub_android_2019.cherkasyguide.routeapi.DirectionsApiFactory
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MapHelper {

    private lateinit var context: Context
    private lateinit var routePolyline: Polyline
    private lateinit var route: RoutesItem

    fun init(context: Context) {
        this.context = context
    }

    private lateinit var builder: LatLngBounds.Builder

    fun getPosition(place: Place): LatLng {
        val lat = place.location?.latitude ?: 49.444566
        val lng = place.location?.longitude ?: 32.059962
        return LatLng(lat, lng)
    }

    fun clearMap(googleMap: GoogleMap) = googleMap.clear()

    fun setUpCamera(markerList: ArrayList<PlaceMarker>, googleMap: GoogleMap) {
        googleMap.moveCamera(updateCamera(markerList))
    }

    private fun updateCamera(markerList: ArrayList<PlaceMarker>): CameraUpdate {
        return if (markerList.toList().size == 1) {
            CameraUpdateFactory.newLatLngZoom(
                markerList[0].position,
                15.0f
            )
        } else {
            CameraUpdateFactory.newLatLngBounds(
                setUpBounds(markerList), 150
            )
        }
    }

    fun setUpClusterOfMarkers(
        googleMap: GoogleMap,
        markerList: ArrayList<PlaceMarker>,
        context: Context
    ) {
        val manager = ClusterManager<PlaceMarker>(context, googleMap)
        manager.renderer = ClusterMarkerRenderer(context, googleMap, manager)
        manager.algorithm = GridBasedAlgorithm()
        manager.addItems(markerList)
        manager.cluster()
    }

    private fun setUpBounds(markerList: ArrayList<PlaceMarker>): LatLngBounds {
        builder = LatLngBounds.builder()
        for (marker in markerList) {
            builder.include(marker.position)
        }
        return builder.build()
    }

    fun getMarkerList(places: Places): ArrayList<PlaceMarker> {
        val placeMarkerList = ArrayList<PlaceMarker>()
        for (place in places) {
            val mItem =
                PlaceMarker(
                    getPosition(place).latitude,
                    getPosition(place).longitude,
                    place.name!!
                )
            placeMarkerList.add(mItem)
        }
        return placeMarkerList
    }

    fun setUpMarker(markersList: ArrayList<PlaceMarker>, googleMap: GoogleMap) {
        for (marker in markersList) {
            val number = (markersList.indexOf(marker) + 1).toString()
            googleMap.addMarker(
                MarkerOptions()
                    .position(marker.position)
                    .title(marker.title)
                    .icon(BitmapDescriptorFactory.fromBitmap(drawRouteMarker(number)))
            )
        }
    }

    fun drawRouteMarker(text: String): Bitmap {
        val b by lazy {
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.pin_route
            )
        }
        val bitmap = resizeIcon(b)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.textSize = 48F
        paint.style = Paint.Style.FILL
        canvas.drawText(
            text,
            (bitmap.width / 3).toFloat(),
            (bitmap.height / 2).toFloat(),
            paint
        )
        return bitmap
    }

    fun resizeIcon(bitmap: Bitmap): Bitmap = Bitmap.createScaledBitmap(bitmap, 80, 130, false)

    fun drawRoute(
        googleMap: GoogleMap,
        origin: String,
        destination: String,
        mode: String,
        waypoints: String
    ) {
        DirectionsApiFactory.getDirectionsApiService(origin, destination, mode, waypoints)
            .enqueue(object : Callback<DirectionResponse> {
                override fun onFailure(call: Call<DirectionResponse>, t: Throwable) {
                    Log.d("error", "ERROR")
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<DirectionResponse>,
                    response: Response<DirectionResponse>
                ) {
                    Log.d("code", response.code().toString())
                    route = response.body()?.routes?.get(0)!!
                    if (::routePolyline.isInitialized) {
                        routePolyline.remove()
                    }
                    drawPolyline(googleMap)
                }
            })
    }

    private fun drawPolyline(googleMap: GoogleMap) {
        val shape = route.overviewPolyline?.points
        Log.d("shape", shape.toString())
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(shape))
            .width(16f)
            .color(context.resources.getColor(R.color.colorSecondaryDark, context.theme))
        routePolyline = googleMap.addPolyline(polyline)
    }

    fun drawStepPolyline(googleMap: GoogleMap, count: Int) {
        val legs = route.legs
        val steps = legs!![count]?.steps
        val path = ArrayList<List<LatLng>>()
        steps?.forEach {
            val points = it?.polyline?.points
            path.add(PolyUtil.decode(points))
        }

        path.forEach {
            routePolyline = googleMap.addPolyline(PolylineOptions()
                .addAll(it)
                .color(Color.BLUE))
        }
    }
}
