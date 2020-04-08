package ru.gidline.app.screen.base.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.RelativeLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeRelativeLayout : RelativeLayout, ShapeView {

    override val styleable: IntArray get() = R.styleable.ShapeRelativeLayout

    override val styleableShape get() = R.styleable.ShapeRelativeLayout_shape

    override val styleableSolidColor get() = R.styleable.ShapeRelativeLayout_solidColor

    override val styleableCornerRadius get() = R.styleable.ShapeRelativeLayout_cornerRadius

    override val styleableCornerTopLeft get() = R.styleable.ShapeRelativeLayout_cornerTopLeft

    override val styleableCornerTopRight get() = R.styleable.ShapeRelativeLayout_cornerTopRight

    override val styleableCornerBottomLeft get() = R.styleable.ShapeRelativeLayout_cornerBottomLeft

    override val styleableCornerBottomRight get() = R.styleable.ShapeRelativeLayout_cornerBottomRight

    override val styleableBorderSize get() = R.styleable.ShapeRelativeLayout_borderSize

    override val styleableBorderColor get() = R.styleable.ShapeRelativeLayout_borderColor

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