package ru.gidline.app.screen.catalog.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import org.jetbrains.anko.dip
import ru.gidline.app.R
import ru.gidline.app.local.model.Place

class ClusterRenderer(
    private val type: String,
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Place>?
) : DefaultClusterRenderer<Place>(context, map, clusterManager) {

    private val iconGenerator = IconGenerator(context)

    private val textStyle = when (type) {
        Place.CONSULATE -> R.style.ClusterConsulate
        Place.MIGRATION -> R.style.ClusterMigration
        else -> 0
    }

    private val strokeSize = context.dip(2).toFloat()

    init {
        clusterManager?.renderer = this
    }

    override fun onBeforeClusterItemRendered(markerItem: Place, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromAsset(markerItem.markerIcon))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Place>, markerOptions: MarkerOptions) {
        iconGenerator.apply {
            setBackground(ClusterDrawable(type, strokeSize))
            setTextAppearance(textStyle)
            setContentPadding(40, 40, 40, 40)
        }
        val icon = iconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }
}