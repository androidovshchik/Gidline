package ru.gidline.app.screen.catalog.map

import android.os.Bundle
import com.google.android.gms.maps.SupportMapFragment

class MapFragment : SupportMapFragment() {

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}