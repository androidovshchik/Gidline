package ru.gidline.app.local.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import de.siegmar.fastcsv.reader.CsvRow
import ru.gidline.app.R
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class Place(val id: Int, row: CsvRow, val zIndex: Float) : ClusterItem {

    val type: String = row.getField(0).trim()

    val country: String = row.getField(1).trim()

    val locality: String = row.getField(2).trim()

    val name: String = row.getField(3).trim()

    val address: String = row.getField(4).trim()

    val latitude = row.getField(5).toDouble()

    val longitude = row.getField(6).toDouble()

    val phones: String = row.getField(7).trim()

    val schedule: String = row.getField(8).trim()

    var distance: Double? = null

    var isActive = false

    val icon: Int
        get() = when (type) {
            CONSULATE -> R.drawable.ic_consulate
            MIGRATION -> R.drawable.ic_migration
            else -> 0
        }

    val markerIcon: Int
        get() = when (type) {
            CONSULATE -> if (isActive) R.drawable.ic_consulate_on else R.drawable.ic_consulate_off
            MIGRATION -> if (isActive) R.drawable.ic_migration_on else R.drawable.ic_migration_off
            else -> 0
        }

    override fun getSnippet() = null

    override fun getTitle() = null

    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    /**
     * Spherical law of cosinuses
     * angle = arccos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(dlon))
     * distance (in meters) = radius of Earth * angle
     */
    fun setDistanceTo(lat: Double, lon: Double) {
        val lat1 = latitude * PI / 180
        val lat2 = lat * PI / 180
        val dLon = (lon - longitude) * PI / 180
        distance = 6378137 * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(dLon))
    }

    companion object {

        const val CONSULATE = "Посольства и консульства"

        const val MIGRATION = "Миграционный центр"
    }
}