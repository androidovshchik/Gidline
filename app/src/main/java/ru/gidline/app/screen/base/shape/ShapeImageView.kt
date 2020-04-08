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

    override val styleable: IntArray get() = R.styleable.ShapeImageView

    override val styleableIcon: Int? get() = null

    override val styleableText: Int? get() = null

    override val styleableShape get() = R.styleable.ShapeImageView_shape

    override val styleableSolidColor get() = R.styleable.ShapeImageView_solidColor

    override val styleableCornerRadius get() = R.styleable.ShapeImageView_cornerRadius

    override val styleableCornerTopLeft get() = R.styleable.ShapeImageView_cornerTopLeft

    override val styleableCornerTopRight get() = R.styleable.ShapeImageView_cornerTopRight

    override val styleableCornerBottomLeft get() = R.styleable.ShapeImageView_cornerBottomLeft

    override val styleableCornerBottomRight get() = R.styleable.ShapeImageView_cornerBottomRight

    override val styleableBorderSize get() = R.styleable.ShapeImageView_borderSize

    override val styleableBorderColor get() = R.styleable.ShapeImageView_borderColor

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}