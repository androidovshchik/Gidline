package ru.gidline.app.screen.base

import android.app.Activity
import android.app.Dialog
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.activityCallback

open class BaseDialog(activity: Activity) : Dialog(activity, R.style.AppDialog), KodeinAware {

    override val kodein by closestKodein()

    inline fun <reified T> activityCallback(action: T.() -> Unit) {
        context.activityCallback(action)
    }
}