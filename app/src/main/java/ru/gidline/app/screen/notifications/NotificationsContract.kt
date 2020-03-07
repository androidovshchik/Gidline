package ru.gidline.app.screen.notifications

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface NotificationsContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}