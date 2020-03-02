package ru.gidline.app.screen.settings

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface SettingsContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}