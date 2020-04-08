package ru.gidline.app.screen.shape

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

@Suppress("LeakingThis")
open class ShapeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), ShapeView {

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}