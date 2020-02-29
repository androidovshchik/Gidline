package ru.gidline.app.screen.splash

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface SplashContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}
