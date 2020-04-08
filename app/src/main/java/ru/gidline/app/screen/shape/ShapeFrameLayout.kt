package ru.gidline.app.screen.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeFrameLayout : FrameLayout, ShapeViewGroup {

    override val styleable: IntArray = R.styleable.ShapeFrameLayout

    override val styleableShape = R.styleable.ShapeFrameLayout_shape

    override val styleableSolidColor = R.styleable.ShapeFrameLayout_solidColor

    override val styleableCornerRadius = R.styleable.ShapeFrameLayout_cornerRadius

    override val styleableCornerTopLeft = R.styleable.ShapeFrameLayout_cornerTopLeft

    override val styleableCornerTopRight = R.styleable.ShapeFrameLayout_cornerTopRight

    override val styleableCornerBottomLeft = R.styleable.ShapeFrameLayout_cornerBottomLeft

    override val styleableCornerBottomRight = R.styleable.ShapeFrameLayout_cornerBottomRight

    override val styleableBorderSize = R.styleable.ShapeFrameLayout_borderSize

    override val styleableBorderColor = R.styleable.ShapeFrameLayout_borderColor

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