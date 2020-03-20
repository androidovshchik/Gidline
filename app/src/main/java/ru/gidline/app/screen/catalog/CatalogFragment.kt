package ru.gidline.app.screen.catalog

import android.Manifest
import android.app.Activity
import android.content.Intent
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

    private val locationCallback = object : LocationCallback() {

        override fun onLocationAvailability(availability: LocationAvailability) {
            Timber.d("onLocationAvailability $availability")
        }

        override fun onLocationResult(result: LocationResult?) {
            result?.lastLocation?.let {
                Timber.i("Last location is $it")
                onLocationResult(SimpleLocation(it))
            } ?: run {
                Timber.w("Last location is null")
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
        if (context.areGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
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

    private fun checkLocation() {
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            })
            .setAlwaysShow(true)
            .build()
        if (!isGpsAvailable) {
            LocationServices.getSettingsClient(requireActivity())
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener {
                    onLocationState(it.locationSettingsStates)
                }
                .addOnFailureListener {
                    onLocationState(null)
                    if (it is ResolvableApiException) {
                        try {
                            it.startResolutionForResult(this, REQUEST_LOCATION)
                        } catch (e: Throwable) {
                            Timber.e(e)
                        }
                    } else {
                        Timber.e(it)
                    }
                }
        }
    }

    override fun onDestroyView() {
        locationClient.removeLocationUpdates(locationCallback)
        tl_catalog.removeOnTabSelectedListener(this)
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_LOCATION -> {
                if (resultCode == Activity.RESULT_OK) {
                    onLocationState(LocationSettingsStates.fromIntent(data))
                } else {
                    checkLocation()
                }
            }
        }
    }

    companion object {

        fun newInstance(): CatalogFragment {
            return CatalogFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}