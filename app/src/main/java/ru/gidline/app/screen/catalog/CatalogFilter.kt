package ru.gidline.app.screen.catalog

import com.google.android.gms.maps.model.LatLng
import ru.gidline.app.R

@Suppress("MemberVisibilityCanBePrivate")
class CatalogFilter {

    var typeId = R.id.ib_all

    var latitude = LAT

    var longitude = LON

    fun toLatLng() = LatLng(latitude, longitude)

    companion object {

        const val LAT = 55.751694

        const val LON = 37.617218
    }
}