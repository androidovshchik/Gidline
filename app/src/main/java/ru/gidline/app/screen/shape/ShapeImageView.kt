package ru.gidline.app.screen.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

@Suppress("LeakingThis")
open class ShapeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), ShapeView {

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}