package ru.gidline.app.screen.notification

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface NotificationContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}