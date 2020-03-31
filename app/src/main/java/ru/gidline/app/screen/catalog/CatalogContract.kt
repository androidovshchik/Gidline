package ru.gidline.app.screen.catalog

import com.google.android.material.tabs.TabLayout
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.catalog.places.PlacesContract

interface CatalogContract {

    interface View : IView, TabLayout.OnTabSelectedListener, PlacesContract.Recycler {

        val catalogFilter: CatalogFilter

        fun showFilter()

        fun updateFilter(id: Int)

        fun onFinishCount()
    }

    interface Presenter : IPresenter<View> {

        fun countDistances()
    }

    interface Radar {

        fun onFilterUpdate()

        fun onLocationUpdate()
    }
}