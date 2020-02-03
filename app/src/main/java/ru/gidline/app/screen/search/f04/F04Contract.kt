package ru.gidline.app.screen.search.f04

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface F04Contract {

    interface View : IView

    interface Presenter : IPresenter<View>
}