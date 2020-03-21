package ru.gidline.app.screen.catalog

import android.content.Context
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.dip
import org.jetbrains.anko.windowManager
import ru.gidline.app.R
import ru.gidline.app.extension.statusBarHeight
import ru.gidline.app.extension.windowSize
import ru.gidline.app.screen.base.BasePopup

class FilterPopup(context: Context) : BasePopup(context) {

    private val topOffset = context.statusBarHeight + context.resources.let {
        it.getDimension(R.dimen.toolbar_height) + it.getDimension(R.dimen.tabs_height)
    }.toInt() + context.dip(6)

    init {
        width = context.windowManager.windowSize.x
        isOutsideTouchable = true
        inputMethodMode = INPUT_METHOD_NOT_NEEDED
        contentView = View.inflate(context, R.layout.popup_filter, null).also {

            it.measureSize(width, View.MeasureSpec.EXACTLY)
            height = it.measuredHeight
        }
    }

    override fun show(anchor: View) {
        contentView.also {
            it.measureSize(width, View.MeasureSpec.EXACTLY)
            height = it.measuredHeight
        }
        showAtLocation(anchor, Gravity.NO_GRAVITY, 0, topOffset)
    }

    override fun onClick(v: View) {

    }
}