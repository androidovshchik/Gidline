package ru.gidline.app.screen.catalog.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.fragment_map.*
import org.jetbrains.anko.dip
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.Preferences
import ru.gidline.app.local.model.Place
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.catalog.CatalogFilter
import kotlin.math.max

class MapFragment : BaseFragment<MapContract.Presenter>(), MapContract.View {

    override val presenter: MapPresenter by instance()

    private val placeRepository: PlaceRepository by instance()

    private val preferences: Preferences by instance()

    private lateinit var mapFragment: SupportMapFragment

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
        mapFragment = childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fab_location.setOnClickListener(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val context = context ?: return
        clusterManager = ClusterManager(context, map)
        clusterRenderer = ClusterRenderer(context, map, clusterManager!!)
        clusterManager!!.setOnClusterClickListener {

            true
        }
        clusterManager!!.setOnClusterItemClickListener {
            lastPlace?.highlightMarker(false)
            lastPlace = it.highlightMarker()
            showPlace(it.id)
            true
        }
        map.also {
            it.uiSettings.isRotateGesturesEnabled = false
            it.setOnCameraIdleListener(clusterManager)
        }
        onFilterUpdate()
        preferences.location?.let {
            updateMyLocation(it.first, it.second)
        }
        mapFragment.view?.viewTreeObserver?.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        mapFragment.view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
        catalogFilter?.let { filter ->
            val places = placeRepository.getAll()
            if (places.isNotEmpty()) {
                val bounds = LatLngBounds.Builder()
                    .include(filter.toLatLng())
                    .include(places[0].position)
                    .build()
                googleMap?.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(bounds, context?.dip(48) ?: 0)
                )
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_location -> {
                locationMarker?.let {
                    googleMap?.let { map ->
                        val zoom = max(11f, map.cameraPosition.zoom)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, zoom))
                    }
                }
            }
        }
    }

    override fun onFilterUpdate() {
        placeRepository.getAll().forEach {
            it.isActive = false
        }
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
            updateMyLocation(it.toLatLng())
        }
    }

    override fun pointPlace(id: Int) {
        val place = placeRepository.getById(id)
        lastPlace?.highlightMarker(false)
        lastPlace = place.highlightMarker()
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(place.position, 15f))
        showPlace(id)
    }

    private fun showPlace(id: Int) {
        PlaceFragment.newInstance(id)
            .show(childFragmentManager, PlaceFragment::class.java.name)
    }

    private fun updateMyLocation(lat: Double, lon: Double) {
        updateMyLocation(LatLng(lat, lon))
    }

    private fun updateMyLocation(position: LatLng) {
        if (locationMarker == null) {
            googleMap?.let { map ->
                locationMarker = map.addMarker(
                    MarkerOptions()
                        .position(position)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_man))
                        .zIndex(100f)
                )
                fab_location.isVisible = true
            }
        } else {
            locationMarker?.position = position
            fab_location.isVisible = true
        }
    }

    private fun Place.highlightMarker(active: Boolean = true): Place {
        isActive = active
        clusterRenderer?.getMarker(this)?.setIcon(BitmapDescriptorFactory.fromResource(markerIcon))
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