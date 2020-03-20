package ru.gidline.app.screen.catalog.places

import ru.gidline.app.local.model.Place
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IRecycler
import ru.gidline.app.screen.base.listener.IView

interface PlacesContract {

    interface View : IView

    interface Recycler : IRecycler<Place>

    interface Presenter : IPresenter<View>
}