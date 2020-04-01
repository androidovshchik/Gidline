package ru.gidline.app.screen.settings

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.gidline.app.screen.base.BasePresenter
import java.lang.ref.WeakReference

class SettingsPresenter(context: Context) : BasePresenter<SettingsContract.View>(context),
    SettingsContract.Presenter {

    override fun getGalleryPath(context: Context, uri: Uri) {
        val contextRef = WeakReference(context)
        job.cancelChildren()
        launch {
            val path = withContext(Dispatchers.IO) {
                contextRef.get()?.run {
                    PathCompat.getFilePath(applicationContext, uri)
                }
            }
            reference.get()?.onPhotoPath(path)
        }
    }
}