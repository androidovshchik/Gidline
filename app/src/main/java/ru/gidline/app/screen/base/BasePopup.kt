@file:Suppress("unused")

package ru.gidline.app.screen.base

import android.content.Context
import android.view.View
import android.widget.PopupWindow
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.extension.activityCallback

abstract class BasePopup(context: Context) : PopupWindow(context), KodeinAware,
    View.OnClickListener {

    override val kodein by closestKodein(context)

    open fun show(anchor: View) {}

    override fun onClick(v: View) {}

    protected fun View.measureSize(
        width: Int = 0,
        wMode: Int = View.MeasureSpec.UNSPECIFIED,
        height: Int = 0,
        hMode: Int = View.MeasureSpec.UNSPECIFIED
    ) {
        measure(
            View.MeasureSpec.makeMeasureSpec(width, wMode),
            View.MeasureSpec.makeMeasureSpec(height, hMode)
        )
    }

    inline fun <reified T> activityCallback(action: T.() -> Unit) {
        contentView.context.activityCallback(action)
    }
}