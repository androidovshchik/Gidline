package ru.gidline.app.screen.base.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), ShapeView {

    override val attributes: IntArray get() = R.styleable.ShapeTextView

    override val indexShape get() = R.styleable.ShapeTextView_shape

    override val indexSolidColor get() = R.styleable.ShapeTextView_solidColor

    override val indexCornerRadius get() = R.styleable.ShapeTextView_cornerRadius

    override val indexCornerTopLeft get() = R.styleable.ShapeTextView_cornerTopLeft

    override val indexCornerTopRight get() = R.styleable.ShapeTextView_cornerTopRight

    override val indexCornerBottomLeft get() = R.styleable.ShapeTextView_cornerBottomLeft

    override val indexCornerBottomRight get() = R.styleable.ShapeTextView_cornerBottomRight

    override val indexBorderSize get() = R.styleable.ShapeTextView_borderSize

    override val indexBorderColor get() = R.styleable.ShapeTextView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}