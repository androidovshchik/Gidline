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
) : AppCompatTextView(context, attrs, defStyleAttr), ShapeView {

    override val styleable: IntArray get() = R.styleable.ShapeTextView

    override val styleableShape get() = R.styleable.ShapeTextView_shape

    override val styleableSolidColor get() = R.styleable.ShapeTextView_solidColor

    override val styleableCornerRadius get() = R.styleable.ShapeTextView_cornerRadius

    override val styleableCornerTopLeft get() = R.styleable.ShapeTextView_cornerTopLeft

    override val styleableCornerTopRight get() = R.styleable.ShapeTextView_cornerTopRight

    override val styleableCornerBottomLeft get() = R.styleable.ShapeTextView_cornerBottomLeft

    override val styleableCornerBottomRight get() = R.styleable.ShapeTextView_cornerBottomRight

    override val styleableBorderSize get() = R.styleable.ShapeTextView_borderSize

    override val styleableBorderColor get() = R.styleable.ShapeTextView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}