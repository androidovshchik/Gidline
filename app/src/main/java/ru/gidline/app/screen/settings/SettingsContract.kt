package ru.gidline.app.screen.settings

import android.content.Context
import android.net.Uri
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface SettingsContract {

    interface View : IView {

        fun onGalleryPath(path: String?)
    }

    interface Presenter : IPresenter<View> {

        fun getGalleryPath(context: Context, uri: Uri)
    }
}