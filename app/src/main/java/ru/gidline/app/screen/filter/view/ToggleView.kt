package ru.gidline.app.screen.filter.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor
import ru.gidline.app.R

@SuppressLint("AppCompatCustomView")
class ToggleView : TextView {

    var isChecked = false
        set(value) {
            field = value
            if (value) {
                backgroundResource = R.drawable.button_active
                textColor = Color.WHITE
            } else {
                backgroundResource = R.drawable.button_inactive
                textColor = Color.parseColor("#813678")
            }
        }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        textSize = 13f
    }

    override fun hasOverlappingRendering() = false
}