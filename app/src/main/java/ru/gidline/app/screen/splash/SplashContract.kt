package ru.gidline.app.screen.splash

import android.content.Context
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface SplashContract {

    interface View : IView {

        fun closeSplash()
    }

    interface Presenter : IPresenter<View> {

        fun initRepos(context: Context)
    }
}
