package ru.gidline.app.screen.settings

import android.content.Context
import android.net.Uri
import android.widget.AdapterView
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface SettingsContract {

    interface View : IView, AdapterView.OnItemSelectedListener {

        fun onPhotoPath(path: String?)
    }

    interface Presenter : IPresenter<View> {

        fun getGalleryPath(context: Context, uri: Uri)
    }
}