package ru.gidline.app.screen.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), ShapeViewGroup {

    override val styleable: IntArray = R.styleable.ShapeImageView

    override val styleableShape = R.styleable.ShapeImageView_shape

    override val styleableSolidColor = R.styleable.ShapeImageView_solidColor

    override val styleableCornerRadius = R.styleable.ShapeImageView_cornerRadius

    override val styleableCornerTopLeft = R.styleable.ShapeImageView_cornerTopLeft

    override val styleableCornerTopRight = R.styleable.ShapeImageView_cornerTopRight

    override val styleableCornerBottomLeft = R.styleable.ShapeImageView_cornerBottomLeft

    override val styleableCornerBottomRight = R.styleable.ShapeImageView_cornerBottomRight

    override val styleableBorderSize = R.styleable.ShapeImageView_borderSize

    override val styleableBorderColor = R.styleable.ShapeImageView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}