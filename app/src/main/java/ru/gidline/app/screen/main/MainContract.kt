package ru.gidline.app.screen.main

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface MainContract {

    interface View : IView {

        fun updateHome(drawable: Int)

        fun setTitle(text: String)
    }

    interface Presenter : IPresenter<View>
}
