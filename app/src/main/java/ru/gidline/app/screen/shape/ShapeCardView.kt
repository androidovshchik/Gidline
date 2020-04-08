package ru.gidline.app.screen.shape

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView

class ShapeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr), ShapeViewGroup {

    init {
        init(attrs)
    }

    override fun hasOverlappingRendering() = false
}