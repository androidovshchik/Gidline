package ru.gidline.app.screen.settings

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface SettingsContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}