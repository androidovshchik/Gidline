package ru.gidline.app.screen.catalog

import android.location.Location
import com.google.android.material.tabs.TabLayout
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface CatalogContract {

    interface View : IView, TabLayout.OnTabSelectedListener {

        val catalogFilter: CatalogFilter

        fun showFilter()

        fun updateFilter(id: Int)
    }

    interface Presenter : IPresenter<View>

    interface Radar {

        fun onLocation(result: Location)
    }
}