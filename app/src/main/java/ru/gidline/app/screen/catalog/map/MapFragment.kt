package ru.gidline.app.screen.catalog.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_map.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.catalog.CatalogFilter

class MapFragment : BaseFragment<MapContract.Presenter>(), MapContract.View {

    override val presenter: MapPresenter by instance()

    private val catalogFilter: CatalogFilter?
        get() {
            parentCallback<CatalogContract.View>(true) {
                return catalogFilter
            }
            return null
        }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_map, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (f_map as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        catalogFilter?.let {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it.toLatLng()))
        }
        //val zoom = CameraUpdateFactory.zoomTo(15f)
        //map.animateCamera(zoom)
    }

    companion object {

        fun newInstance(): MapFragment {
            return MapFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}