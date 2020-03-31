package ru.gidline.app.screen.search

import android.content.Context
import android.graphics.Matrix
import android.view.Gravity
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import coil.api.load
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.popup_chips.view.*
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.windowManager
import ru.gidline.app.R
import ru.gidline.app.extension.statusBarHeight
import ru.gidline.app.extension.windowSize
import ru.gidline.app.screen.base.BasePopup
import ru.gidline.app.screen.base.listener.IView

class ChipsPopup(context: Context) : BasePopup(context) {

    private val topOffset = context.statusBarHeight + context.resources.let {
        it.getDimension(R.dimen.toolbar_height) + it.getDimension(R.dimen.search_bar_height)
    }.toInt()

    init {
        val screen = context.windowManager.windowSize
        width = matchParent
        height = screen.y - topOffset
        inputMethodMode = INPUT_METHOD_NEEDED
        setBackgroundDrawable(null)
        contentView = View.inflate(context, R.layout.popup_chips, null).also {
            it.iv_background.apply {
                load(R.drawable.background)
                imageMatrix = Matrix().apply {
                    setTranslate(0f, context.statusBarHeight.toFloat() - topOffset)
                }
            }
            it.cg_chips.children.forEach { child ->
                child.setOnClickListener(this)
            }
        }
    }

    override fun show(anchor: View) {
        showAtLocation(anchor, Gravity.NO_GRAVITY, 0, topOffset)
    }

    fun filter(input: String) {
        contentView.cg_chips.children.forEach {
            if (it is Chip) {
                it.isVisible = it.text.toString().contains(input, true)
            }
        }
    }

    override fun onClick(v: View) {
        activityCallback<IView> {
            when (val topFragment = topFragment) {
                is SearchContract.View -> {
                    topFragment.changeSearch((v as Chip).text.toString())
                }
            }
        }
    }
}