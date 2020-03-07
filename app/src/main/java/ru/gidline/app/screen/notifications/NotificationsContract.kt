package ru.gidline.app.screen.notifications

import ru.gidline.app.local.dto.Bell
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IRecycler
import ru.gidline.app.screen.base.listener.IView

interface NotificationsContract {

    interface View : IView, IRecycler<Bell>

    interface Presenter : IPresenter<View>
}