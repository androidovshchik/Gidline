package ru.gidline.app.screen.base.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeFrameLayout : FrameLayout, ShapeView {

    override val shapeAttrs: IntArray get() = R.styleable.ShapeFrameLayout

    override val indexShape get() = R.styleable.ShapeFrameLayout_shape

    override val indexSolidColor get() = R.styleable.ShapeFrameLayout_solidColor

    override val indexCornerRadius get() = R.styleable.ShapeFrameLayout_cornerRadius

    override val indexCornerTopLeft get() = R.styleable.ShapeFrameLayout_cornerTopLeft

    override val indexCornerTopRight get() = R.styleable.ShapeFrameLayout_cornerTopRight

    override val indexCornerBottomLeft get() = R.styleable.ShapeFrameLayout_cornerBottomLeft

    override val indexCornerBottomRight get() = R.styleable.ShapeFrameLayout_cornerBottomRight

    override val indexBorderSize get() = R.styleable.ShapeFrameLayout_borderSize

    override val indexBorderColor get() = R.styleable.ShapeFrameLayout_borderColor

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}