package ru.gidline.app.screen.base.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeLinearLayout : LinearLayout, ShapeView {

    override val shapeAttrs: IntArray get() = R.styleable.ShapeLinearLayout

    override val indexShape get() = R.styleable.ShapeLinearLayout_shape

    override val indexSolidColor get() = R.styleable.ShapeLinearLayout_solidColor

    override val indexCornerRadius get() = R.styleable.ShapeLinearLayout_cornerRadius

    override val indexCornerTopLeft get() = R.styleable.ShapeLinearLayout_cornerTopLeft

    override val indexCornerTopRight get() = R.styleable.ShapeLinearLayout_cornerTopRight

    override val indexCornerBottomLeft get() = R.styleable.ShapeLinearLayout_cornerBottomLeft

    override val indexCornerBottomRight get() = R.styleable.ShapeLinearLayout_cornerBottomRight

    override val indexBorderSize get() = R.styleable.ShapeLinearLayout_borderSize

    override val indexBorderColor get() = R.styleable.ShapeLinearLayout_borderColor

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