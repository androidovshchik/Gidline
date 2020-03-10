package ru.gidline.app.screen.notifications.notification

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class NotificationPresenter(context: Context) : BasePresenter<NotificationContract.View>(context),
    NotificationContract.Presenter