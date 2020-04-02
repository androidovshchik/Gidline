package ru.gidline.app.screen.common.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import ru.gidline.app.R
import ru.gidline.app.extension.use

@SuppressLint("AppCompatCustomView")
class ImageBox : ImageView {

    private var icon: Drawable? = null

    var isChecked = false
        set(value) {
            field = value
            setImageDrawable(if (value) icon else null)
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

    @SuppressLint("Recycle")
    private fun init(attrs: AttributeSet?) {
        scaleType = ScaleType.FIT_CENTER
        adjustViewBounds = true
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.ImageBox).use {
                icon = getDrawable(R.styleable.ImageBox_icon)
                background = getDrawable(R.styleable.ImageBox_background)
            }
        }
    }

    override fun hasOverlappingRendering() = false
}