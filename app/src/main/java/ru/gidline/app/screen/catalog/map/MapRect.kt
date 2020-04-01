package ru.gidline.app.screen.catalog.map

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlin.math.PI
import kotlin.math.cos

class MapRect {

    var minLat = 360.0

    var maxLat = -360.0

    var minLon = 360.0

    var maxLon = -360.0

    private var updateCount = 0

    val isValid: Boolean
        get() = updateCount >= 1

    val centerLat: Double
        get() = (maxLat + minLat) / 2

    val centerLon: Double
        get() = (maxLon + minLon) / 2

    fun update(location: Location) {
        update(location.latitude, location.longitude)
    }

    fun update(latLng: LatLng) {
        update(latLng.latitude, latLng.longitude)
    }

    fun update(lat: Double?, lon: Double?) {
        if (lat != null && lon != null) {
            if (lat < minLat) {
                minLat = lat
            }
            if (lat > maxLat) {
                maxLat = lat
            }
            if (lon < minLon) {
                minLon = lon
            }
            if (lon > maxLon) {
                maxLon = lon
            }
            updateCount++
        }
    }

    /**
     * Length in meters of 1° of latitude = always 111.32 km
     * Length in meters of 1° of longitude = 111.32 km * cos(latitude)
     */
    fun measure() {
        if (isValid) {
            val minLength = 2 //km
            val latLength = 111.32 //km
            if (maxLat - minLat < minLength / latLength) {
                minLat -= minLength / latLength / 2
                maxLat += minLength / latLength / 2
            }
            val lonLength = latLength * cos(centerLat * PI / 180)
            if (maxLon - minLon < minLength / lonLength) {
                minLon -= minLength / lonLength / 2
                maxLon += minLength / lonLength / 2
            }
        }
    }
}