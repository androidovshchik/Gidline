package ru.gidline.app.screen.main

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.view.children
import org.jetbrains.anko.dip
import ru.gidline.app.R
import ru.gidline.app.extension.statusBarHeight

class MenuPopup(context: Context) : PopupWindow(context), View.OnClickListener {

    private val topOffset = context.statusBarHeight

    init {
        width = context.dip(280)
        isOutsideTouchable = true
        inputMethodMode = INPUT_METHOD_NOT_NEEDED
        contentView = View.inflate(context, R.layout.popup_menu, null).also {
            (it as ViewGroup).children.forEach { child ->
                if (child is MenuLayout) {
                    child.setOnClickListener(this)
                }
            }
        }
    }

    fun show(anchor: View) {
        showAtLocation(anchor, Gravity.NO_GRAVITY, 0, topOffset)
    }

    override fun onClick(v: View) {
        (contentView as ViewGroup).also {
            it.children.forEach { child ->
                if (child is MenuLayout) {
                    child.toggle(it.indexOfChild(v))
                }
            }
        }
    }
}