package ru.gidline.app.screen.main

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface MainContract {

    interface View : IView {

        fun setTitle(text: String)

        fun updateHome(drawable: Int)

        fun updateAction(text: String?)

        fun toggleBottomNav(show: Boolean)
    }

    interface Presenter : IPresenter<View>
}
