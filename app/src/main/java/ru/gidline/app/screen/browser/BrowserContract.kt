package ru.gidline.app.screen.browser

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface BrowserContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}