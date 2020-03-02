package ru.gidline.app.screen.notifications

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class NotificationsPresenter(context: Context) : BasePresenter<NotificationsContract.View>(context),
    NotificationsContract.Presenter