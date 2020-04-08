package ru.gidline.app.screen.base.shape

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import org.jetbrains.anko.dip
import ru.gidline.app.extension.use

interface ShapeView {

    val styleable: IntArray

    val styleableShape: Int

    val styleableSolidColor: Int

    val styleableCornerRadius: Int

    val styleableCornerTopLeft: Int

    val styleableCornerTopRight: Int

    val styleableCornerBottomLeft: Int

    val styleableCornerBottomRight: Int

    val styleableBorderSize: Int

    val styleableBorderColor: Int

    fun getContext(): Context

    fun setBackground(background: Drawable)

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n", "DefaultLocale")
    fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            getContext().obtainStyledAttributes(attrs, styleable).use {
                getString(styleableShape)?.toLowerCase()?.let {
                    setBackground(GradientDrawable().apply {
                        shape = when (it) {
                            "rect", "rectangle" -> GradientDrawable.RECTANGLE
                            "oval" -> GradientDrawable.OVAL
                            "ring" -> GradientDrawable.RING
                            "line" -> GradientDrawable.LINE
                            else -> return
                        }
                        if (hasValue(styleableSolidColor)) {
                            setColor(getColor(styleableSolidColor, 0))
                        }
                        if (it == "rect" || it == "rectangle") {
                            if (hasValue(styleableCornerRadius)) {
                                cornerRadius = getDimension(styleableCornerRadius, 0f)
                            } else {
                                val tl = getDimension(styleableCornerTopLeft, 0f)
                                val tr = getDimension(styleableCornerTopRight, 0f)
                                val br = getDimension(styleableCornerBottomRight, 0f)
                                val bl = getDimension(styleableCornerBottomLeft, 0f)
                                cornerRadii = floatArrayOf(tl, tl, tr, tr, br, br, bl, bl)
                            }
                        }
                        if (hasValue(styleableBorderColor)) {
                            setStroke(
                                getDimensionPixelSize(styleableBorderSize, getContext().dip(1.5f)),
                                getColor(styleableBorderColor, 0)
                            )
                        }
                    })
                }
            }
        }
    }
}