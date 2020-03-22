package ru.gidline.app.screen.catalog

import android.content.Context
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.popup_filter.view.*
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
            it.ib_close.setOnClickListener(this)
            it.ib_all.setOnClickListener(this)
            it.ib_consulate.setOnClickListener(this)
            it.ib_migration.setOnClickListener(this)
            it.mb_apply.setOnClickListener(this)
            it.measureSize(width, View.MeasureSpec.EXACTLY)
            height = it.measuredHeight
        }
    }

    override fun show(anchor: View) {
        showAtLocation(anchor, Gravity.NO_GRAVITY, 0, topOffset)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_close -> {
                dismiss()
            }
            R.id.ib_all -> {

            }
            R.id.ib_consulate -> {

            }
            R.id.ib_migration -> {

            }
            R.id.mb_apply -> {

            }
        }
    }
}