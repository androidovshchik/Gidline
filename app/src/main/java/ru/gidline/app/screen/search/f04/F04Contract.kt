package ru.gidline.app.screen.search.f04

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface F04Contract {

    interface View : IView

    interface Presenter : IPresenter<View>
}