package ru.gidline.app.screen.splash

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface SplashContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}
