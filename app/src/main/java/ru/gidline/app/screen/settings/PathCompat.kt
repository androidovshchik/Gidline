@file:Suppress("FunctionName")

package ru.gidline.app.screen.settings

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import androidx.core.content.FileProvider
import ru.gidline.app.extension.isKitkatPlus
import timber.log.Timber
import java.io.File

@Suppress("SpellCheckingInspection")
object PathCompat {

    fun getPhotoFile(context: Context): File? = context.run {
        getExternalFilesDir(null)?.also {
            it.mkdirs()
            return File(it, "photo.jpg")
        }
        return null
    }

    fun getPhotoUri(context: Context): Uri? = context.run {
        return FileProvider.getUriForFile(
            applicationContext,
            "$packageName.fileprovider",
            getPhotoFile(context) ?: return null
        )
    }

    @WorkerThread
    fun getFilePath(context: Context, uri: Uri): String? = context.run {
        return try {
            when {
                !isKitkatPlus() -> getDataColumn(uri, null, null)
                else -> getPathKitkatPlus(uri)
            }
        } catch (e: Throwable) {
            Timber.e(e)
            null
        }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("NewApi", "DefaultLocale")
    private fun Context.getPathKitkatPlus(uri: Uri): String? {
        when {
            DocumentsContract.isDocumentUri(applicationContext, uri) -> {
                val docId = DocumentsContract.getDocumentId(uri)
                when {
                    uri.isExternalStorageDocument -> {
                        val parts = docId.split(":")
                        if ("primary".equals(parts[0], true)) {
                            return "${Environment.getExternalStorageDirectory()}/${parts[1]}"
                        }
                    }
                    uri.isDownloadsDocument -> {
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            docId.toLong()
                        )
                        return getDataColumn(contentUri, null, null)
                    }
                    uri.isMediaDocument -> {
                        val parts = docId.split(":")
                        val contentUri = when (parts[0].toLowerCase()) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> return null
                        }
                        return getDataColumn(contentUri, "_id=?", arrayOf(parts[1]))
                    }
                }
            }
            "content".equals(uri.scheme, true) -> {
                return if (uri.isGooglePhotosUri) {
                    uri.lastPathSegment
                } else {
                    getDataColumn(uri, null, null)
                }
            }
            "file".equals(uri.scheme, true) -> {
                return uri.path
            }
        }
        return null
    }

    private fun Context.getDataColumn(uri: Uri, selection: String?, args: Array<String>?): String? {
        contentResolver?.query(uri, arrayOf("_data"), selection, args, null)?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndexOrThrow("_data"))
            }
        }
        return null
    }

    private val Uri.isExternalStorageDocument: Boolean
        get() = authority == "com.android.externalstorage.documents"

    private val Uri.isDownloadsDocument: Boolean
        get() = authority == "com.android.providers.downloads.documents"

    private val Uri.isMediaDocument: Boolean
        get() = authority == "com.android.providers.media.documents"

    private val Uri.isGooglePhotosUri: Boolean
        get() = authority == "com.google.android.apps.photos.content"
}