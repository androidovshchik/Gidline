@file:Suppress("unused")

package ru.gidline.app.screen.base

import android.content.Context
import android.view.View
import android.widget.PopupWindow
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.extension.makeCallback
import java.lang.ref.WeakReference

private class PopupObserver(listener: PopupWindow.OnDismissListener) :
    PopupWindow.OnDismissListener {

    private val reference = WeakReference(listener)

    override fun onDismiss() {
        reference.get()?.onDismiss()
    }
}

@Suppress("LeakingThis")
abstract class BasePopup(context: Context) : PopupWindow(context), KodeinAware,
    PopupWindow.OnDismissListener, View.OnClickListener {

    override val kodein by closestKodein(context)

    private val observer = PopupObserver(this)

    open fun show(anchor: View) {}

    override fun onClick(v: View) {}

    override fun onDismiss() {}

    inline fun <reified T> makeCallback(action: T.() -> Unit) {
        contentView.context.makeCallback(action)
    }
}