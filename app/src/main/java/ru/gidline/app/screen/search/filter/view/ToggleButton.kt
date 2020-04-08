package ru.gidline.app.screen.search.filter.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import org.jetbrains.anko.textColor
import ru.gidline.app.R
import ru.gidline.app.screen.base.shape.ShapeTextView

class ToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeTextView(context, attrs, defStyleAttr) {

    var isChecked = false
        set(value) {
            field = value
            textColor = if (value) {
                setBackgroundResource(R.drawable.button_active)
                Color.WHITE
            } else {
                setBackgroundResource(R.drawable.button_inactive)
                Color.parseColor("#813678")
            }
        }

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n")
    override fun init(attrs: AttributeSet?) {
        super.init(attrs)
        textSize = 13f
    }

    override fun hasOverlappingRendering() = false
}