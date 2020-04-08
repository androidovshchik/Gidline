package ru.gidline.app.screen.base.shape

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeLinearLayout : LinearLayout, ShapeView {

    override val styleable: IntArray get() = R.styleable.ShapeLinearLayout

    override val styleableShape get() = R.styleable.ShapeLinearLayout_shape

    override val styleableSolidColor get() = R.styleable.ShapeLinearLayout_solidColor

    override val styleableCornerRadius get() = R.styleable.ShapeLinearLayout_cornerRadius

    override val styleableCornerTopLeft get() = R.styleable.ShapeLinearLayout_cornerTopLeft

    override val styleableCornerTopRight get() = R.styleable.ShapeLinearLayout_cornerTopRight

    override val styleableCornerBottomLeft get() = R.styleable.ShapeLinearLayout_cornerBottomLeft

    override val styleableCornerBottomRight get() = R.styleable.ShapeLinearLayout_cornerBottomRight

    override val styleableBorderSize get() = R.styleable.ShapeLinearLayout_borderSize

    override val styleableBorderColor get() = R.styleable.ShapeLinearLayout_borderColor

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