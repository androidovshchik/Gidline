package ru.gidline.app.screen.catalog.places

import ru.gidline.app.local.model.Place
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IRecycler
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.catalog.CatalogFilter

interface PlacesContract {

    interface View : IView, Recycler, CatalogContract.Radar

    interface Recycler : IRecycler<Place>

    interface Presenter : IPresenter<View> {

        fun applyDistances(catalogFilter: CatalogFilter)
    }
}