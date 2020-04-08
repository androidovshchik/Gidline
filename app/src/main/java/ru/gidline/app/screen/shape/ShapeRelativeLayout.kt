package ru.gidline.app.screen.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.RelativeLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeRelativeLayout : RelativeLayout, ShapeViewGroup {

    override val styleable: IntArray = R.styleable.ShapeRelativeLayout

    override val styleableShape = R.styleable.ShapeRelativeLayout_shape

    override val styleableSolidColor = R.styleable.ShapeRelativeLayout_solidColor

    override val styleableCornerRadius = R.styleable.ShapeRelativeLayout_cornerRadius

    override val styleableCornerTopLeft = R.styleable.ShapeRelativeLayout_cornerTopLeft

    override val styleableCornerTopRight = R.styleable.ShapeRelativeLayout_cornerTopRight

    override val styleableCornerBottomLeft = R.styleable.ShapeRelativeLayout_cornerBottomLeft

    override val styleableCornerBottomRight = R.styleable.ShapeRelativeLayout_cornerBottomRight

    override val styleableBorderSize = R.styleable.ShapeRelativeLayout_borderSize

    override val styleableBorderColor = R.styleable.ShapeRelativeLayout_borderColor

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