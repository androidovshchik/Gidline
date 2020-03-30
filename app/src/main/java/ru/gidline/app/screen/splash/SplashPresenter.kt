package ru.gidline.app.screen.splash

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.generic.instance
import ru.gidline.app.BuildConfig
import ru.gidline.app.local.Preferences
import ru.gidline.app.local.repository.BellRepository
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.local.repository.VacancyRepository
import ru.gidline.app.screen.base.BasePresenter
import ru.gidline.app.screen.catalog.CatalogFilter

class SplashPresenter(context: Context) : BasePresenter<SplashContract.View>(context),
    SplashContract.Presenter {

    private val preferences: Preferences by instance()

    private val bellRepository: BellRepository by instance()

    private val vacancyRepository: VacancyRepository by instance()

    private val placeRepository: PlaceRepository by instance()

    override fun initRepos(context: Context) {
        launch {
            withContext(Dispatchers.IO) {
                bellRepository.initData(context)
                vacancyRepository.initData(context)
                placeRepository.initData(context)
                val (lat, lon) = preferences.location ?: CatalogFilter.LAT to CatalogFilter.LON
                placeRepository.getAll().forEach {
                    it.setDistanceTo(lat, lon)
                }
            }
            delay(if (BuildConfig.DEBUG) 200L else 2000L)
            reference.get()?.closeSplash()
        }
    }
}
