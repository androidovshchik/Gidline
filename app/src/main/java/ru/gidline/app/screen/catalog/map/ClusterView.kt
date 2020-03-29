package ru.gidline.app.screen.catalog.map

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import ru.gidline.app.R

@SuppressLint("AppCompatCustomView")
class ClusterView : TextView {

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
        id = R.id.amu_text
        setTextColor(Color.parseColor("#d18bc9"))
        setBackgroundResource(R.drawable.background_consulate)
    }

    override fun hasOverlappingRendering() = false
}