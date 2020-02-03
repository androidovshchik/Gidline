package ru.gidline.app.screen.main

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface MainContract {

    interface View : IView {

        fun updateHome(drawable: Int)

        fun updateAction(text: String?)
    }

    interface Presenter : IPresenter<View>
}
