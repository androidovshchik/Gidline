package ru.gidline.app.screen.shape

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr), ShapeViewGroup {

    override val styleable: IntArray = R.styleable.ShapeCardView

    override val styleableShape = R.styleable.ShapeCardView_shape

    override val styleableSolidColor = R.styleable.ShapeCardView_solidColor

    override val styleableCornerRadius = R.styleable.ShapeCardView_cornerRadius

    override val styleableCornerTopLeft = R.styleable.ShapeCardView_cornerTopLeft

    override val styleableCornerTopRight = R.styleable.ShapeCardView_cornerTopRight

    override val styleableCornerBottomLeft = R.styleable.ShapeCardView_cornerBottomLeft

    override val styleableCornerBottomRight = R.styleable.ShapeCardView_cornerBottomRight

    override val styleableBorderSize = R.styleable.ShapeCardView_borderSize

    override val styleableBorderColor = R.styleable.ShapeCardView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}