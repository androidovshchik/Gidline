package ru.gidline.app.screen.catalog.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import ru.gidline.app.local.model.Place

class ClusterRenderer(
    type: String,
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Place>?
) : DefaultClusterRenderer<Place>(context, map, clusterManager) {

    private val iconGenerator = IconGenerator(context).apply {
        setContentView(ClusterView(context))
        setBackground(null)
    }

    init {
        clusterManager?.renderer = this
    }

    override fun onBeforeClusterItemRendered(markerItem: Place, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromAsset(markerItem.markerIcon))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Place>, markerOptions: MarkerOptions) {
        val icon = iconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }
}