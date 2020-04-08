package ru.gidline.app.screen.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeLinearLayout : LinearLayout, ShapeViewGroup {

    override val styleable: IntArray = R.styleable.ShapeLinearLayout

    override val styleableShape = R.styleable.ShapeLinearLayout_shape

    override val styleableSolidColor = R.styleable.ShapeLinearLayout_solidColor

    override val styleableCornerRadius = R.styleable.ShapeLinearLayout_cornerRadius

    override val styleableCornerTopLeft = R.styleable.ShapeLinearLayout_cornerTopLeft

    override val styleableCornerTopRight = R.styleable.ShapeLinearLayout_cornerTopRight

    override val styleableCornerBottomLeft = R.styleable.ShapeLinearLayout_cornerBottomLeft

    override val styleableCornerBottomRight = R.styleable.ShapeLinearLayout_cornerBottomRight

    override val styleableBorderSize = R.styleable.ShapeLinearLayout_borderSize

    override val styleableBorderColor = R.styleable.ShapeLinearLayout_borderColor

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