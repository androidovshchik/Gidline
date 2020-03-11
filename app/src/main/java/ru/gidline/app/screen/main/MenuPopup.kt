package ru.gidline.app.screen.main

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import kotlinx.android.synthetic.main.popup_menu.view.*
import org.jetbrains.anko.dip
import ru.gidline.app.R
import ru.gidline.app.extension.statusBarHeight
import ru.gidline.app.screen.base.BasePopup
import ru.gidline.app.screen.main.view.MenuLayout

class MenuPopup(context: Context) : BasePopup(context) {

    private val topOffset = context.statusBarHeight

    init {
        width = context.dip(280)
        isOutsideTouchable = true
        inputMethodMode = INPUT_METHOD_NOT_NEEDED
        setBackgroundDrawable(null)
        contentView = View.inflate(context, R.layout.popup_menu, null).also {
            it.ll_menu.children.forEach { child ->
                if (child is MenuLayout) {
                    child.setOnClickListener(this)
                }
            }
            it.measureSize(width)
            height = it.measuredHeight
        }
    }

    override fun show(anchor: View) {
        contentView.also {
            it.hl_menu.updateData()
            it.ll_menu.children.forEach { child ->
                if (child is MenuLayout) {
                    child.toggle(-1)
                }
            }
            it.measureSize(width)
            height = it.measuredHeight
        }
        showAtLocation(anchor, Gravity.NO_GRAVITY, 0, topOffset)
    }

    override fun onClick(v: View) {
        (v.parent as ViewGroup).also {
            it.children.forEach { child ->
                if (child is MenuLayout) {
                    child.toggle(it.indexOfChild(v))
                }
            }
        }
    }
}