package ru.gidline.app.screen.main

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface MainContract {

    interface View : IView {

        fun setTitle(text: String)

        fun notifyBell(all: Int, unread: Int = 0)

        fun updateHome(drawable: Int)

        fun updateAction(text: String?)

        fun toggleBottomNav(show: Boolean)
    }

    interface Presenter : IPresenter<View>
}
