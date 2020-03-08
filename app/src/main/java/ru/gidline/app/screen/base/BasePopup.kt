@file:Suppress("unused")

package ru.gidline.app.screen.base

import android.content.Context
import android.view.View
import android.widget.PopupWindow
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.extension.makeCallback

@Suppress("LeakingThis")
abstract class BasePopup(context: Context) : PopupWindow(context), KodeinAware,
    View.OnClickListener {

    override val kodein by closestKodein(context)

    open fun show(anchor: View) {}

    override fun onClick(v: View) {}

    inline fun <reified T> makeCallback(action: T.() -> Unit) {
        contentView.context.makeCallback(action)
    }
}