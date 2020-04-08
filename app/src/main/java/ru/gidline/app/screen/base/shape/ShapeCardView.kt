package ru.gidline.app.screen.base.shape

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr), ShapeView {

    override val styleable: IntArray get() = R.styleable.ShapeCardView

    override val styleableShape get() = R.styleable.ShapeCardView_shape

    override val styleableSolidColor get() = R.styleable.ShapeCardView_solidColor

    override val styleableCornerRadius get() = R.styleable.ShapeCardView_cornerRadius

    override val styleableCornerTopLeft get() = R.styleable.ShapeCardView_cornerTopLeft

    override val styleableCornerTopRight get() = R.styleable.ShapeCardView_cornerTopRight

    override val styleableCornerBottomLeft get() = R.styleable.ShapeCardView_cornerBottomLeft

    override val styleableCornerBottomRight get() = R.styleable.ShapeCardView_cornerBottomRight

    override val styleableBorderSize get() = R.styleable.ShapeCardView_borderSize

    override val styleableBorderColor get() = R.styleable.ShapeCardView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}