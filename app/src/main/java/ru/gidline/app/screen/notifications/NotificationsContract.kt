package ru.gidline.app.screen.notifications

import ru.gidline.app.local.model.Bell
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IRecycler
import ru.gidline.app.screen.base.listener.IView

interface NotificationsContract {

    interface View : IView, Recycler {

        fun refreshData()
    }

    interface Recycler : IRecycler<Bell> {

        fun onItemDeleted(id: Int)
    }

    interface Presenter : IPresenter<View>
}