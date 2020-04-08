package ru.gidline.app.screen.search.filter.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView
import org.jetbrains.anko.textColor
import ru.gidline.app.R

class ToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialTextView(context, attrs, defStyleAttr) {

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

    init {
        init(attrs)
    }

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n")
    private fun init(attrs: AttributeSet?) {
        textSize = 13f
    }

    override fun hasOverlappingRendering() = false
}