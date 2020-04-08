package ru.gidline.app.screen.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import ru.gidline.app.R
import ru.gidline.app.extension.use
import ru.gidline.app.screen.base.shape.ShapeImageView

class ImageBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeImageView(context, attrs, defStyleAttr) {

    private var icon: Drawable? = null

    var isChecked = false
        set(value) {
            field = value
            setImageDrawable(if (value) icon else null)
        }

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n")
    override fun init(attrs: AttributeSet?) {
        super.init(attrs)
        scaleType = ScaleType.FIT_CENTER
        adjustViewBounds = true
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.ImageBox).use {
                icon = getDrawable(R.styleable.ImageBox_icon)
            }
        }
    }

    override fun hasOverlappingRendering() = false
}