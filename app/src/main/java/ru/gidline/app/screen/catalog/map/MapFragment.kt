package ru.gidline.app.screen.catalog.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.fragment_map.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.model.Place
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.catalog.CatalogFilter

class MapFragment : BaseFragment<MapContract.Presenter>(), MapContract.View {

    override val presenter: MapPresenter by instance()

    private val placeRepository: PlaceRepository by instance()

    private var googleMap: GoogleMap? = null

    private var clusterManager: ClusterManager<Place>? = null

    private var clusterRenderer: ClusterRenderer? = null

    private var locationMarker: Marker? = null

    private var lastPlace: Place? = null

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
        (childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment).getMapAsync(this)
        fab_location.setOnClickListener(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val context = context ?: return
        clusterManager = ClusterManager(context, map)
        clusterRenderer = ClusterRenderer(context, map, clusterManager!!)
        clusterManager!!.setOnClusterItemClickListener { place ->
            lastPlace = place.highlightMarker()
            showPlace(place.id)
            true
        }
        map.also {
            it.uiSettings.isRotateGesturesEnabled = false
            it.setOnCameraIdleListener(clusterManager)
        }
        onFilterUpdate()
        catalogFilter?.let {
            locationMarker = map.addMarker(
                MarkerOptions()
                    .position(it.toLatLng())
                    .icon(BitmapDescriptorFactory.fromAsset("marker/man_shadow.png"))
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(it.toLatLng(), 8f))
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_location -> {
                locationMarker?.let {
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLng(it.position))
                }
            }
        }
    }

    override fun onFilterUpdate() {
        clusterManager?.apply {
            clearItems()
            addItems(
                when (catalogFilter?.typeId) {
                    R.id.ib_consulate -> placeRepository.getByType(Place.CONSULATE)
                    R.id.ib_migration -> placeRepository.getByType(Place.MIGRATION)
                    else -> placeRepository.getAll()
                }
            )
            cluster()
        }
    }

    override fun onLocationUpdate() {
        catalogFilter?.let {
            locationMarker?.position = it.toLatLng()
            fab_location.isVisible = true
        }
    }

    override fun pointPlace(id: Int) {
        val place = placeRepository.getById(id)
        lastPlace = place.highlightMarker()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(place.position, 15f))
        showPlace(id)
    }

    private fun showPlace(id: Int) {
        PlaceFragment.newInstance(id)
            .show(childFragmentManager, PlaceFragment::class.java.name)
    }

    private fun Place.highlightMarker(): Place {
        if (this != lastPlace) {
            lastPlace?.highlightMarker()
        }
        isActive = !isActive
        clusterRenderer?.getMarker(this)?.setIcon(BitmapDescriptorFactory.fromAsset(markerIcon))
        return this
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