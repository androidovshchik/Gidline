package ru.gidline.app.screen.catalog

import com.google.android.gms.maps.model.LatLng
import ru.gidline.app.R

@Suppress("MemberVisibilityCanBePrivate")
class CatalogFilter {

    var typeId = R.id.ib_all

    var latitude = 55.751694

    var longitude = 37.617218

    fun toLatLng() = LatLng(latitude, longitude)
}