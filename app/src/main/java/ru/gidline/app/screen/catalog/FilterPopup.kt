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
import ru.gidline.app.screen.base.listener.IView

class FilterPopup(context: Context) : BasePopup(context) {

    private val topOffset = context.statusBarHeight + context.resources.let {
        it.getDimension(R.dimen.toolbar_height) + it.getDimension(R.dimen.tabs_height)
    }.toInt() + context.dip(6)

    private val hOffset = context.dip(4)

    init {
        width = context.windowManager.windowSize.x - 2 * hOffset
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

    fun show(anchor: View, catalogFilter: CatalogFilter) {
        if (!isShowing) {
            updateBoxes(catalogFilter.typeId)
            showAtLocation(anchor, Gravity.NO_GRAVITY, hOffset, topOffset)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_close -> dismiss()
            R.id.ib_all, R.id.ib_consulate, R.id.ib_migration -> updateBoxes(v.id)
            R.id.mb_apply -> {
                activityCallback<IView> {
                    when (val topFragment = topFragment) {
                        is CatalogContract.View -> {
                            contentView.also {
                                topFragment.updateFilter(
                                    when {
                                        it.ib_all.isChecked -> R.id.ib_all
                                        it.ib_consulate.isChecked -> R.id.ib_consulate
                                        it.ib_migration.isChecked -> R.id.ib_migration
                                        else -> return
                                    }
                                )
                            }
                        }
                    }
                }
                dismiss()
            }
        }
    }

    private fun updateBoxes(id: Int) {
        contentView.also {
            it.ib_all.isChecked = id == R.id.ib_all
            it.ib_consulate.isChecked = id == R.id.ib_consulate
            it.ib_migration.isChecked = id == R.id.ib_migration
        }
    }
}