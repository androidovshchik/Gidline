package ru.gidline.app.screen.notification

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface NotificationContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}