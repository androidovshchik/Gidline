package ru.gidline.app.local.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import de.siegmar.fastcsv.reader.CsvRow
import ru.gidline.app.R

class Place(val id: Int, row: CsvRow) : ClusterItem {

    val type: String = row.getField(0)

    val country: String = row.getField(1)

    val locality: String = row.getField(2)

    val name: String = row.getField(3)

    val address: String = row.getField(4)

    val latitude = row.getField(5).toDouble()

    val longitude = row.getField(6).toDouble()

    val phones: String = row.getField(7)

    val schedule: String = row.getField(8)

    val icon: Int
        get() = when (type) {
            CONSULATE -> R.drawable.ic_consulate
            MIGRATION -> R.drawable.ic_migration_center
            else -> 0
        }

    val markerOn: Int
        get() = when (type) {
            CONSULATE -> R.drawable.mark_consulate_on
            MIGRATION -> R.drawable.mark_migration_on
            else -> 0
        }

    val markerOff: Int
        get() = when (type) {
            CONSULATE -> R.drawable.mark_consulate_off
            MIGRATION -> R.drawable.mark_migration_off
            else -> 0
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

        private const val CONSULATE = "Посольства и консульства"

        private const val MIGRATION = "Миграционный центр"
    }
}