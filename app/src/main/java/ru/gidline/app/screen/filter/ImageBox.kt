package ru.gidline.app.screen.filter

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import org.jetbrains.anko.backgroundResource
import ru.gidline.app.R

class ImageBox : ImageView {

    var isChecked = false
        set(value) {
            field = value
            setImageResource(
                if (value) {
                    R.drawable.daw_violet
                } else 0
            )
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
        scaleType = ScaleType.CENTER_CROP
        adjustViewBounds = true
        backgroundResource = R.drawable.checkbox
    }

    override fun hasOverlappingRendering() = false
}