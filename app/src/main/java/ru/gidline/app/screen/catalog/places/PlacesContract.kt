package ru.gidline.app.screen.catalog.places

import ru.gidline.app.local.model.Place
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IRecycler
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.catalog.CatalogContract

interface PlacesContract {

    interface View : IView, CatalogContract.Radar

    interface Recycler : IRecycler<Place>

    interface Presenter : IPresenter<View>
}