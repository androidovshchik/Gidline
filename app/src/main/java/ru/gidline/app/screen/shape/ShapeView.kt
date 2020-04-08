package ru.gidline.app.screen.shape

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import ru.gidline.app.R
import ru.gidline.app.extension.use

interface ShapeView {

    fun getContext(): Context

    fun setBackground(background: Drawable)

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n", "DefaultLocale")
    fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val instance = this
            getContext().obtainStyledAttributes(attrs, R.styleable.ShapeView).use {
                if (hasValue(R.styleable.ShapeView_layout) && instance is ViewGroup) {
                    View.inflate(getContext(), getInt(R.styleable.ShapeView_layout, 0), instance)
                }
                getString(R.styleable.ShapeView_shape)?.toLowerCase()?.let {
                    setBackground(GradientDrawable().apply {
                        shape = when (it) {
                            "rect", "rectangle" -> GradientDrawable.RECTANGLE
                            "oval" -> GradientDrawable.OVAL
                            "ring" -> GradientDrawable.RING
                            else -> GradientDrawable.LINE
                        }
                        if (hasValue(R.styleable.ShapeView_solidColor)) {
                            setColor(getColor(R.styleable.ShapeView_solidColor, 0))
                        }
                        if (it == "rect" || it == "rectangle") {
                            if (hasValue(R.styleable.ShapeView_cornerRadius)) {
                                cornerRadius = getDimension(R.styleable.ShapeView_cornerRadius, 0f)
                            } else {
                                val tl = getDimension(R.styleable.ShapeView_cornerTopLeft, 0f)
                                val tr = getDimension(R.styleable.ShapeView_cornerTopRight, 0f)
                                val bl = getDimension(R.styleable.ShapeView_cornerBottomRight, 0f)
                                val br = getDimension(R.styleable.ShapeView_cornerBottomLeft, 0f)
                                cornerRadii = floatArrayOf(tl, tl, tr, tr, br, br, bl, bl)
                            }
                        }
                        if (hasValue(R.styleable.ShapeView_borderSize)) {
                            setStroke(
                                getDimensionPixelSize(R.styleable.ShapeView_borderSize, 0),
                                getColor(R.styleable.ShapeView_borderColor, Color.TRANSPARENT)
                            )
                        }
                    })
                }
            }
        }
    }
}