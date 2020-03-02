package ru.gidline.app.screen.notifications

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface NotificationsContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}