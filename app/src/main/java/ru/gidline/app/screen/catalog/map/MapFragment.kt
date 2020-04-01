package ru.gidline.app.screen.catalog.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
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

    private var locationMarker: Marker? = null

    private var lastPlace: Place? = null

    private var lastMarker: Marker? = null

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
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val context = context ?: return
        clusterManager = ClusterManager(context, map)
        val clusterRenderer = ClusterRenderer(context, map, clusterManager!!)
        clusterManager!!.setOnClusterItemClickListener { place ->
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

    override fun onFilterUpdate() {
        catalogFilter?.let {
            clusterManager?.apply {
                clearItems()
                if (fill) {
                    addItems(placeRepository.getByType(type))
                }
                cluster()
            }
            clusterManagerConsulate?.notifyItems(
                Place.CONSULATE,
                it.typeId == R.id.ib_all || it.typeId == R.id.ib_consulate
            )
            clusterManagerMigration?.notifyItems(
                Place.MIGRATION,
                it.typeId == R.id.ib_all || it.typeId == R.id.ib_migration
            )
        }
    }

    override fun onLocationUpdate() {
        catalogFilter?.let {
            locationMarker?.position = it.toLatLng()
        }
    }

    override fun pointPlace(id: Int) {
        val place = placeRepository.getById(id)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(place.position, 15f))
        showPlace(id)
    }

    private fun showPlace(id: Int) {
        PlaceFragment.newInstance(id)
            .show(childFragmentManager, PlaceFragment::class.java.name)
    }

    private fun ClusterRenderer.onClusterItemClick(place: Place) {
        /*val marker = getMarker(place)
        try {
        isActive = !isActive
        marker?.setIcon(BitmapDescriptorFactory.fromAsset(markerIcon))
            lastPlace?.notify(lastMarker)//isActive
            place.notify(marker)
        } catch (e: Throwable) {
            clusterManagerConsulate?.notifyItems(Place.CONSULATE)
            clusterManagerMigration?.notifyItems(Place.MIGRATION)
        }
        lastPlace = place
        lastMarker = marker*/
        showPlace(place.id)
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