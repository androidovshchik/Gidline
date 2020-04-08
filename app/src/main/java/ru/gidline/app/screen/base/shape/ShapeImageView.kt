package ru.gidline.app.screen.base.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), ShapeView {

    override val attributes: IntArray get() = R.styleable.ShapeImageView

    override val indexShape get() = R.styleable.ShapeImageView_shape

    override val indexSolidColor get() = R.styleable.ShapeImageView_solidColor

    override val indexCornerRadius get() = R.styleable.ShapeImageView_cornerRadius

    override val indexCornerTopLeft get() = R.styleable.ShapeImageView_cornerTopLeft

    override val indexCornerTopRight get() = R.styleable.ShapeImageView_cornerTopRight

    override val indexCornerBottomLeft get() = R.styleable.ShapeImageView_cornerBottomLeft

    override val indexCornerBottomRight get() = R.styleable.ShapeImageView_cornerBottomRight

    override val indexBorderSize get() = R.styleable.ShapeImageView_borderSize

    override val indexBorderColor get() = R.styleable.ShapeImageView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}