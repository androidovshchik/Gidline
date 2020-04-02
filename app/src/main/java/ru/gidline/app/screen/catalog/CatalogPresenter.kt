package ru.gidline.app.screen.catalog

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.generic.instance
import ru.gidline.app.extension.sortListBy
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.base.BasePresenter

class CatalogPresenter(context: Context) : BasePresenter<CatalogContract.View>(context),
    CatalogContract.Presenter {

    private val placeRepository: PlaceRepository by instance()

    override fun countDistances() {
        val catalogFilter = reference.get()?.catalogFilter ?: return
        job.cancelChildren()
        launch {
            withContext(Dispatchers.Default) {
                placeRepository.getAll().apply {
                    forEach {
                        it.setDistanceTo(catalogFilter.latitude, catalogFilter.longitude)
                    }
                    sortListBy { it.distance }
                }
            }
            reference.get()?.onFinishCount()
        }
    }
}