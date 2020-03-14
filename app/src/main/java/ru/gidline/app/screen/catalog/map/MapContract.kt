package ru.gidline.app.screen.catalog.map

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface MapContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}