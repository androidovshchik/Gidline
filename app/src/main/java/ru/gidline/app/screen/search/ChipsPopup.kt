package ru.gidline.app.screen.search

import android.content.Context
import android.graphics.Matrix
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import androidx.core.view.children
import androidx.core.view.isVisible
import coil.api.load
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.popup_chips.view.*
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.windowManager
import ru.gidline.app.R
import ru.gidline.app.extension.makeCallback
import ru.gidline.app.extension.setTextSelection
import ru.gidline.app.extension.statusBarHeight
import ru.gidline.app.extension.windowSize
import ru.gidline.app.screen.base.listener.IView

class ChipsPopup(context: Context) : PopupWindow(context), View.OnClickListener {

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
                    setTranslate(0f, 0f - topOffset + context.statusBarHeight)
                }
            }
            it.cg_chips.children.forEach { child ->
                child.setOnClickListener(this)
            }
        }
    }

    fun show(anchor: View) {
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
        v.context.makeCallback<IView> {
            when (val topFragment = topFragment) {
                is SearchFragment -> {
                    topFragment.et_search?.setTextSelection((v as Chip).text)
                }
            }
        }
    }
}