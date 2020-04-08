package ru.gidline.app.screen.base.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.RelativeLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeRelativeLayout : RelativeLayout, ShapeView {

    override val attributes: IntArray get() = R.styleable.ShapeRelativeLayout

    override val indexShape get() = R.styleable.ShapeRelativeLayout_shape

    override val indexSolidColor get() = R.styleable.ShapeRelativeLayout_solidColor

    override val indexCornerRadius get() = R.styleable.ShapeRelativeLayout_cornerRadius

    override val indexCornerTopLeft get() = R.styleable.ShapeRelativeLayout_cornerTopLeft

    override val indexCornerTopRight get() = R.styleable.ShapeRelativeLayout_cornerTopRight

    override val indexCornerBottomLeft get() = R.styleable.ShapeRelativeLayout_cornerBottomLeft

    override val indexCornerBottomRight get() = R.styleable.ShapeRelativeLayout_cornerBottomRight

    override val indexBorderSize get() = R.styleable.ShapeRelativeLayout_borderSize

    override val indexBorderColor get() = R.styleable.ShapeRelativeLayout_borderColor

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