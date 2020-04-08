package ru.gidline.app.screen.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.gidline.app.R

@Suppress("LeakingThis")
open class ShapeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), ShapeViewGroup {

    override val styleable: IntArray = R.styleable.ShapeTextView

    override val styleableShape = R.styleable.ShapeTextView_shape

    override val styleableSolidColor = R.styleable.ShapeTextView_solidColor

    override val styleableCornerRadius = R.styleable.ShapeTextView_cornerRadius

    override val styleableCornerTopLeft = R.styleable.ShapeTextView_cornerTopLeft

    override val styleableCornerTopRight = R.styleable.ShapeTextView_cornerTopRight

    override val styleableCornerBottomLeft = R.styleable.ShapeTextView_cornerBottomLeft

    override val styleableCornerBottomRight = R.styleable.ShapeTextView_cornerBottomRight

    override val styleableBorderSize = R.styleable.ShapeTextView_borderSize

    override val styleableBorderColor = R.styleable.ShapeTextView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}