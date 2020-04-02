package ru.gidline.app.screen.catalog

import android.Manifest
import android.graphics.Matrix
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import com.chibatching.kotpref.bulk
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_catalog.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.areGranted
import ru.gidline.app.local.Preferences
import ru.gidline.app.local.model.Place
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.catalog.map.MapContract
import timber.log.Timber

class CatalogFragment : BaseFragment<CatalogContract.Presenter>(), CatalogContract.View {

    override val presenter: CatalogPresenter by instance()

    private val placeRepository: PlaceRepository by instance()

    private val preferences: Preferences by instance()

    private val filterPopup: FilterPopup by instance()

    override val catalogFilter = CatalogFilter()

    private val locationClient: FusedLocationProviderClient by instance()

    private val locationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setFastestInterval(5000L)
        .setInterval(5000L)

    private var lastListTime = -LIST_UPDATE_TIME

    private val locationCallback = object : LocationCallback(), OnFailureListener {

        override fun onLocationAvailability(availability: LocationAvailability) {
            Timber.i(availability.toString())
            if (!availability.isLocationAvailable) {
                LocationServices.getSettingsClient(activity ?: return)
                    .checkLocationSettings(
                        LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                            .setAlwaysShow(true)
                            .build()
                    )
                    .addOnFailureListener(this)
            }
        }

        override fun onLocationResult(result: LocationResult?) {
            result?.lastLocation?.let {
                preferences.bulk {
                    latitude = it.latitude.toFloat()
                    longitude = it.longitude.toFloat()
                }
                catalogFilter.apply {
                    latitude = it.latitude
                    longitude = it.longitude
                }
                val uptime = SystemClock.elapsedRealtime()
                if (uptime - lastListTime >= LIST_UPDATE_TIME) {
                    lastListTime = uptime
                    presenter.countDistances()
                }
                findFragment<CatalogContract.Radar>(R.id.f_map)?.onLocationUpdate()
            }
        }

        override fun onFailure(e: Exception) {
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(requireActivity(), REQUEST_DIALOG)
                } catch (e: Throwable) {
                    Timber.e(e)
                }
            } else {
                Timber.e(e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences.location?.let {
            catalogFilter.apply {
                latitude = it.first
                longitude = it.second
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iv_background.apply {
            load(R.drawable.background)
            imageMatrix = Matrix().apply {
                setTranslate(0f, -resources.getDimension(R.dimen.toolbar_height))
            }
        }
        tl_catalog.also {
            it.addTab(it.newTab().setText("СПИСОК"))
            it.addTab(it.newTab().setText("КАРТА"))
            it.addOnTabSelectedListener(this)
        }
        hideFragment(R.id.f_map)
    }

    override fun onStart() {
        super.onStart()
        if (context?.areGranted(Manifest.permission.ACCESS_FINE_LOCATION) == true) {
            locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION
            )
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabSelected(tab: TabLayout.Tab) {
        if (tl_catalog.selectedTabPosition == 0) {
            hideFragment(R.id.f_map)
            showFragment(R.id.f_places)
        } else {
            hideFragment(R.id.f_places)
            showFragment(R.id.f_map)
        }
    }

    override fun showFilter() {
        filterPopup.show(tl_catalog, catalogFilter)
    }

    override fun updateFilter(id: Int) {
        catalogFilter.typeId = id
        placeRepository.getAll().forEach {
            it.isActive = false
        }
        findFragment<CatalogContract.Radar>(R.id.f_places)?.onFilterUpdate()
        findFragment<CatalogContract.Radar>(R.id.f_map)?.onFilterUpdate()
    }

    override fun onItemSelected(position: Int, item: Place) {
        tl_catalog.getTabAt(1)?.select()
        findFragment<MapContract.View>(R.id.f_map)?.pointPlace(item.id)
    }

    override fun onFinishCount() {
        findFragment<CatalogContract.Radar>(R.id.f_places)?.onLocationUpdate()
    }

    override fun onStop() {
        locationClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    override fun onDestroyView() {
        filterPopup.dismiss()
        tl_catalog.removeOnTabSelectedListener(this)
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, p: Array<out String>, r: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION -> {
                if (context?.areGranted(Manifest.permission.ACCESS_FINE_LOCATION) == true) {
                    locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                }
            }
        }
    }

    companion object {

        private const val REQUEST_DIALOG = 200

        private const val REQUEST_LOCATION = 2000

        private const val LIST_UPDATE_TIME = 60_000L

        fun newInstance(): CatalogFragment {
            return CatalogFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}