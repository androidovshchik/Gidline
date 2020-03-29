package ru.gidline.app.screen.catalog.map

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import ru.gidline.app.R
import ru.gidline.app.local.model.Place

class ClusterRenderer(context: Context, map: GoogleMap, clusterManager: ClusterManager<Place>?) :
    DefaultClusterRenderer<Place>(context, map, clusterManager) {

    private val iconGenerator = IconGenerator(context)

    private val consulate = ContextCompat.getDrawable(context, R.drawable.background_consulate)

    private val migration = ContextCompat.getDrawable(context, R.drawable.background_migration)

    init {
        clusterManager?.renderer = this
    }

    override fun onBeforeClusterItemRendered(markerItem: Place, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromAsset(markerItem.marker))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Place>, markerOptions: MarkerOptions) {
        var consulates = 0
        var migrations = 0
        cluster.items.forEach {
            when (it.type) {
                Place.CONSULATE -> consulates++
                Place.MIGRATION -> migrations++
                else -> {
                }
            }
        }
        iconGenerator.setBackground(if (consulates >= migrations) consulate else migration)
        iconGenerator.setTextAppearance(
            if (consulates >= migrations) {
                R.style.ClusterConsulate
            } else {
                R.style.ClusterMigration
            }
        )
        val icon = iconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }
}