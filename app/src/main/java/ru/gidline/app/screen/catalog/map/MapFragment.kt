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

    private var clusterRendererConsulate: ClusterRenderer? = null

    private var clusterRendererMigration: ClusterRenderer? = null

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
        clusterRendererConsulate = ClusterRenderer(context, map, clusterManagerConsulate)
        clusterRendererMigration = ClusterRenderer(context, map, clusterManagerMigration)
        map.also {
            it.uiSettings.isRotateGesturesEnabled = false
            it.setOnCameraIdleListener(clusterManagerConsulate)
        }
        catalogFilter?.let {
            locationMarker = map.addMarker(
                MarkerOptions()
                    .position(it.toLatLng())
                    .icon(BitmapDescriptorFactory.fromAsset("marker/man_shadow.png"))
            )
            map.moveCamera(CameraUpdateFactory.newLatLng(it.toLatLng()))
        }
        clusterManagerConsulate?.apply {
            setOnClusterItemClickListener {
                val marker = clusterRendererConsulate?.getMarker(it)
                lastPlace?.notify(lastMarker)
                it.notify(marker)
                lastPlace = it
                lastMarker = marker
                PlaceFragment.newInstance(it.id)
                    .show(childFragmentManager, PlaceFragment::class.java.name)
                true
            }
            addItems(placeRepository.getAll())
            cluster()
        }
    }

    override fun onNewLocation() {
        catalogFilter?.let {
            locationMarker?.position = it.toLatLng()
        }
    }

    private fun Place.notify(marker: Marker?) {
        isActive = !isActive
        marker?.setIcon(BitmapDescriptorFactory.fromAsset(this.marker))
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