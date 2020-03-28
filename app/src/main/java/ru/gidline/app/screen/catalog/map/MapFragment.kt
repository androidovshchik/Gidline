package ru.gidline.app.screen.catalog.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
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

    private var markerManager: MarkerManager? = null

    private var clusterManager: ClusterManager<Place>? = null

    private var clusterRenderer: ClusterRenderer? = null

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
        markerManager = MarkerManager(map)
        clusterManager = ClusterManager(context, map, markerManager)
        clusterRenderer = ClusterRenderer(context, map, clusterManager)
        clusterManager?.setOnClusterItemClickListener {
            clusterRenderer?.getMarker(it)
            //Timber.e(it.id.toString())
            false
        }
        map.setOnCameraIdleListener(clusterManager)

        catalogFilter?.let {
            map.moveCamera(CameraUpdateFactory.newLatLng(it.toLatLng()))
        }
        map.animateCamera(CameraUpdateFactory.zoomTo(11f))
        clusterManager?.apply {
            addItems(placeRepository.getAll())
            cluster()
        }
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