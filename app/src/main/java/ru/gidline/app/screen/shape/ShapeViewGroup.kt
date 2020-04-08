package ru.gidline.app.screen.shape

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import ru.gidline.app.R
import ru.gidline.app.extension.use

interface ShapeViewGroup {

    fun getContext(): Context

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n")
    fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            getContext().obtainStyledAttributes(attrs, R.styleable.CategoryLayout).use {
                /*val shape = GradientDrawable().apply {
                    this.c
                }
                shape.shape = GradientDrawable.OVAL
                shape.setColor(Color.WHITE)
                shape.setStroke(2, Color.BLACK)
                View.inflate(getContext(), R.layout.merge_category, this as ViewGroup)
                iv_icn.setImageDrawable(getDrawable(R.styleable.CategoryLayout_icon))
                getString(R.styleable.CategoryLayout_text)?.let {
                    tv_ame.text = it
                }
                getDimension(R.styleable.CategoryLayout_offset, 0f).let {
                    (tv_ame.layoutParams as ViewGroup.MarginLayoutParams).marginStart =
                        it.roundToInt()
                }*/
            }
        }
    }
}