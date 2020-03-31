package com.geekhub_android_2019.cherkasyguide.maputils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.Places
import com.google.android.gms.maps.model.*

object Utils {

    private lateinit var builder: LatLngBounds.Builder

    private fun getPosition(place: Place): LatLng {
        val lat = place.location?.latitude ?: 49.444566
        val lng = place.location?.longitude ?: 32.059962
        return LatLng(lat, lng)
    }

    fun setUpBounds(markerList: MutableIterable<PlaceMarker>): LatLngBounds {
        builder = LatLngBounds.builder()
        for (marker in markerList) {
            builder.include(marker.position)
        }
        return builder.build()
    }

    fun getMarkerList(places: Places): ArrayList<PlaceMarker> {
        val placeMarkerList = ArrayList<PlaceMarker>()
        for (place in places) {
            val mItem: PlaceMarker =
                PlaceMarker(
                    getPosition(place).latitude,
                    getPosition(place).longitude,
                    place.name!!
                )
            placeMarkerList.add(mItem)
        }
        return placeMarkerList
    }

    fun resizeBitmap(bitmap: Bitmap): Bitmap = Bitmap.createScaledBitmap(bitmap, 63, 110, false)

    private fun createMarker(text: String, bitmap: Bitmap): Bitmap {
        val mBitmap = resizeBitmap(bitmap)
        val canvas = Canvas(mBitmap)
        val paint = Paint()
        paint.textSize = 40F
        paint.style = Paint.Style.FILL
        canvas.drawText(
            text,
            (mBitmap.width / 3).toFloat(),
            (mBitmap.height / 2).toFloat(),
            paint
        )
        return mBitmap
    }

    fun markerOptions(marker: PlaceMarker, number: String, bitmap: Bitmap): MarkerOptions {
        return MarkerOptions()
            .position(marker.position)
            .title(marker.title)
            .icon(
                BitmapDescriptorFactory.fromBitmap(
                    createMarker(number, bitmap)
                )
            )
    }

    fun polylineOptions(list: List<LatLng>, width: Float, color: Int): PolylineOptions {
        return PolylineOptions()
            .addAll(list)
            .width(width)
            .color(color)
    }
}
