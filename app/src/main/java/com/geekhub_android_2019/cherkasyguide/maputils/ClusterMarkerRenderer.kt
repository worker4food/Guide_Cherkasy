package com.geekhub_android_2019.cherkasyguide.maputils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.geekhub_android_2019.cherkasyguide.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ClusterMarkerRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<PlaceMarker>
) :
    DefaultClusterRenderer<PlaceMarker>(context, map, clusterManager),
    ClusterManager.OnClusterClickListener<PlaceMarker> {

    private val googleMap: GoogleMap = map
    private val b = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.pin_blue
    )

    init {
        clusterManager.setOnClusterClickListener(this)
        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)
    }

    override fun onBeforeClusterItemRendered(item: PlaceMarker, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        val icon = Bitmap.createScaledBitmap(b, 100, 100, false)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
        markerOptions.title(item.title)
    }

    override fun onClusterClick(cluster: Cluster<PlaceMarker>): Boolean {
        val builder = LatLngBounds.builder()
        for (marker in cluster.items) {
            builder.include(marker.position)
        }
        val bounds = builder.build()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
        return true
    }
}
