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

    protected fun View.measureSize(width: Int = 0, height: Int = 0) {
        measure(
            View.MeasureSpec.makeMeasureSpec(
                width,
                if (width > 0) View.MeasureSpec.EXACTLY else View.MeasureSpec.UNSPECIFIED
            ),
            View.MeasureSpec.makeMeasureSpec(
                height,
                if (height > 0) View.MeasureSpec.EXACTLY else View.MeasureSpec.UNSPECIFIED
            )
        )
    }

    inline fun <reified T> activityCallback(action: T.() -> Unit) {
        contentView.context.activityCallback(action)
    }
}