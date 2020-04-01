package ru.gidline.app.screen.catalog.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import ru.gidline.app.local.model.Place

class ClusterRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Place>
) : DefaultClusterRenderer<Place>(context, map, clusterManager) {

    private val consulateGenerator = IconGenerator(context).apply {
        setBackground(null)
        setContentView(ClusterView(context, Place.CONSULATE))
    }

    private val migrationGenerator = IconGenerator(context).apply {
        setBackground(null)
        setContentView(ClusterView(context, Place.MIGRATION))
    }

    init {
        clusterManager.renderer = this
    }

    override fun onBeforeClusterItemRendered(markerItem: Place, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(markerItem.markerIcon))
            .zIndex(markerItem.zIndex)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Place>, markerOptions: MarkerOptions) {
        val icon =
            if (cluster.items.count { it.type == Place.CONSULATE } > cluster.items.size / 2) {
                consulateGenerator.makeIcon(cluster.size.toString())
            } else {
                migrationGenerator.makeIcon(cluster.size.toString())
            }
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
            .zIndex(1f)
    }

    /**
     * https://github.com/googlemaps/android-maps-utils/issues/655#issuecomment-604391630
     */
    override fun onClusterUpdated(cluster: Cluster<Place>?, marker: Marker?) {}
}