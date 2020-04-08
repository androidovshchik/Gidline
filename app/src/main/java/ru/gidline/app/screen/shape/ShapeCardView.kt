package ru.gidline.app.screen.shape

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView

@Suppress("LeakingThis")
open class ShapeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr), ShapeView {

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}