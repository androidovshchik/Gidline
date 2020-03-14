package ru.gidline.app.screen.catalog

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface CatalogContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}