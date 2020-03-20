package ru.gidline.app.screen.catalog

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_catalog.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.areGranted
import ru.gidline.app.screen.base.BaseFragment
import timber.log.Timber

class CatalogFragment : BaseFragment<CatalogContract.Presenter>(), CatalogContract.View {

    override val presenter: CatalogPresenter by instance()

    private val locationClient: FusedLocationProviderClient by instance()

    private val locationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setFastestInterval(5000L)
        .setInterval(5000L)

    private val locationCallback = object : LocationCallback() {

        override fun onLocationAvailability(availability: LocationAvailability) {
            Timber.d("onLocationAvailability $availability")
            if (!availability.isLocationAvailable) {
                LocationServices.getSettingsClient(activity ?: return)
                    .checkLocationSettings(
                        LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                            .setAlwaysShow(true)
                            .build()
                    )
                    .addOnFailureListener {
                        if (it is ResolvableApiException) {
                            try {
                                it.startResolutionForResult(requireActivity(), REQUEST_DIALOG)
                            } catch (e: Throwable) {
                                Timber.e(e)
                            }
                        } else {
                            Timber.e(it)
                        }
                    }
            }
        }

        override fun onLocationResult(result: LocationResult?) {
            result?.lastLocation?.let {
                Timber.d("Last location is $it")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tl_catalog.also {
            it.addTab(it.newTab().setText("СПИСОК"))
            it.addTab(it.newTab().setText("КАРТА"))
            it.addOnTabSelectedListener(this)
        }
        hideFragment(R.id.f_map)
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
        when (tl_catalog.selectedTabPosition) {
            0 -> {
                hideFragment(R.id.f_map)
                showFragment(R.id.f_places)
            }
            else -> {
                hideFragment(R.id.f_places)
                showFragment(R.id.f_map)
            }
        }
    }

    override fun onDestroyView() {
        locationClient.removeLocationUpdates(locationCallback)
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

        fun newInstance(): CatalogFragment {
            return CatalogFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}