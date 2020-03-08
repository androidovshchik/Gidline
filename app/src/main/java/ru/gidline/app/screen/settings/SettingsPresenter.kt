package ru.gidline.app.screen.settings

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.gidline.app.screen.base.BasePresenter
import timber.log.Timber
import java.lang.ref.WeakReference

class SettingsPresenter(context: Context) : BasePresenter<SettingsContract.View>(context),
    SettingsContract.Presenter {

    override fun getGalleryPath(context: Context, uri: Uri) {
        val contextRef = WeakReference(context)
        launch {
            withContext(Dispatchers.IO) {
                contextRef.get()?.apply {
                    val path = PathCompat.getFilePath(applicationContext, uri)
                    Timber.d("Gallery path: $path")
                    reference.get()?.onPhotoPath(path)
                }
            }
        }
    }
}