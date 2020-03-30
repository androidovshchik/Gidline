package ru.gidline.app.screen.catalog.places

import android.content.Context
import org.kodein.di.generic.instance
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.base.BasePresenter
import ru.gidline.app.screen.catalog.CatalogFilter

class PlacesPresenter(context: Context) : BasePresenter<PlacesContract.View>(context),
    PlacesContract.Presenter {

    private val placeRepository: PlaceRepository by instance()

    override fun applyDistances(catalogFilter: CatalogFilter) {
        placeRepository.getAll().apply {
            forEach {
                it.setDistanceTo(catalogFilter.latitude, catalogFilter.longitude)
            }
            sortBy { it.distance }
        }
    }
}