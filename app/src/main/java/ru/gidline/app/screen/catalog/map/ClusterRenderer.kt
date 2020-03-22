package ru.gidline.app.screen.catalog.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import ru.gidline.app.local.model.Place

@Suppress("DEPRECATION")
class ClusterRenderer(context: Context?, map: GoogleMap, clusterManager: ClusterManager<Place>?) :
    DefaultClusterRenderer<Place>(context, map, clusterManager) {

    init {
        clusterManager?.renderer = this
    }

    override fun onBeforeClusterItemRendered(markerItem: Place, markerOptions: MarkerOptions?) {
        markerOptions?.icon(BitmapDescriptorFactory.fromResource(markerItem.markerOn))
    }
}