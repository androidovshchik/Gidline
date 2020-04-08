package ru.gidline.app.screen.base.shape

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import org.jetbrains.anko.dip
import ru.gidline.app.extension.use

interface ShapeView {

    val attributes: IntArray

    val indexShape: Int

    val indexSolidColor: Int

    val indexCornerRadius: Int

    val indexCornerTopLeft: Int

    val indexCornerTopRight: Int

    val indexCornerBottomLeft: Int

    val indexCornerBottomRight: Int

    val indexBorderSize: Int

    val indexBorderColor: Int

    fun getContext(): Context

    fun setBackground(background: Drawable)

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n", "DefaultLocale")
    fun init(attrs: AttributeSet?) {
        getContext().obtainStyledAttributes(attrs ?: return, attributes).use {
            getString(indexShape)?.toLowerCase()?.let {
                setBackground(GradientDrawable().apply {
                    shape = when (it) {
                        "rect", "rectangle" -> GradientDrawable.RECTANGLE
                        "oval" -> GradientDrawable.OVAL
                        "ring" -> GradientDrawable.RING
                        "line" -> GradientDrawable.LINE
                        else -> return
                    }
                    if (hasValue(indexSolidColor)) {
                        setColor(getColor(indexSolidColor, 0))
                    }
                    if (it == "rect" || it == "rectangle") {
                        if (hasValue(indexCornerRadius)) {
                            cornerRadius = getDimension(indexCornerRadius, 0f)
                        } else {
                            val tl = getDimension(indexCornerTopLeft, 0f)
                            val tr = getDimension(indexCornerTopRight, 0f)
                            val br = getDimension(indexCornerBottomRight, 0f)
                            val bl = getDimension(indexCornerBottomLeft, 0f)
                            cornerRadii = floatArrayOf(tl, tl, tr, tr, br, br, bl, bl)
                        }
                    }
                    if (hasValue(indexBorderColor)) {
                        setStroke(
                            getDimensionPixelSize(indexBorderSize, getContext().dip(1.5f)),
                            getColor(indexBorderColor, 0)
                        )
                    }
                })
            }
        }
    }
}