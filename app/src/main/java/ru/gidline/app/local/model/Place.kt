package ru.gidline.app.local.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import de.siegmar.fastcsv.reader.CsvRow
import ru.gidline.app.R

class Place(val id: Int, row: CsvRow) : ClusterItem {

    val type: String = row.getField(0).trim()

    val country: String = row.getField(1).trim()

    val locality: String = row.getField(2).trim()

    val name: String = row.getField(3).trim()

    val address: String = row.getField(4).trim()

    val latitude = row.getField(5).toDouble()

    val longitude = row.getField(6).toDouble()

    val phones: String = row.getField(7).trim()

    val schedule: String = row.getField(8).trim()

    var isActive = false

    val icon: Int
        get() = when (type) {
            CONSULATE -> R.drawable.ic_consulate
            MIGRATION -> R.drawable.ic_migration
            else -> 0
        }

    val marker: String?
        get() = when (type) {
            CONSULATE -> "marker/consulate_${if (isActive) "on" else "off"}.png"
            MIGRATION -> "marker/migration_${if (isActive) "on" else "off"}.png"
            else -> null
        }

    val cluster: Int
        get() = when (type) {
            CONSULATE -> R.drawable.background_consulate
            MIGRATION -> R.drawable.background_migration
            else -> 0
        }

    override fun getSnippet() = null

    override fun getTitle() = null

    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    companion object {

        const val CONSULATE = "Посольства и консульства"

        const val MIGRATION = "Миграционный центр"
    }
}