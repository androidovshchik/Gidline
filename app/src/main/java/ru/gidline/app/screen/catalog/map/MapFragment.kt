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

    private var clusterManagerConsulate: ClusterManager<Place>? = null

    private var clusterManagerMigration: ClusterManager<Place>? = null

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
        val context = context ?: return
        clusterManagerConsulate = ClusterManager(context, map)
        clusterManagerMigration = ClusterManager(context, map)
        val clusterRendererConsulate =
            ClusterRenderer(Place.CONSULATE, context, map, clusterManagerConsulate)
        val clusterRendererMigration =
            ClusterRenderer(Place.MIGRATION, context, map, clusterManagerMigration)
        map.also {
            it.uiSettings.isRotateGesturesEnabled = false
            it.setOnCameraIdleListener {
                clusterManagerConsulate?.onCameraIdle()
                clusterManagerMigration?.onCameraIdle()
            }
        }
        clusterManagerConsulate?.init(Place.CONSULATE, clusterRendererConsulate)
        clusterManagerMigration?.init(Place.MIGRATION, clusterRendererMigration)
        catalogFilter?.let {
            locationMarker = map.addMarker(
                MarkerOptions()
                    .position(it.toLatLng())
                    .icon(BitmapDescriptorFactory.fromAsset("marker/man_shadow.png"))
            )
            map.moveCamera(CameraUpdateFactory.newLatLng(it.toLatLng()))
        }
    }

    override fun onNewLocation() {
        catalogFilter?.let {
            locationMarker?.position = it.toLatLng()
        }
    }

    private fun ClusterManager<Place>.init(type: String, renderer: ClusterRenderer) {
        setOnClusterItemClickListener { place ->
            val marker = renderer.getMarker(place)
            try {
                lastPlace?.notify(lastMarker)
                place.notify(marker)
            } catch (e: Throwable) {
                clusterManagerConsulate?.notify(Place.CONSULATE)
                clusterManagerMigration?.notify(Place.MIGRATION)
            }
            lastPlace = place
            lastMarker = marker
            PlaceFragment.newInstance(place.id)
                .show(childFragmentManager, PlaceFragment::class.java.name)
            true
        }
        notify(type)
    }

    private fun ClusterManager<Place>.notify(type: String) {
        clearItems()
        addItems(placeRepository.getByType(type))
        cluster()
    }

    @Throws(Throwable::class)
    private fun Place.notify(marker: Marker?) {
        isActive = !isActive
        marker?.setIcon(BitmapDescriptorFactory.fromAsset(markerIcon))
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