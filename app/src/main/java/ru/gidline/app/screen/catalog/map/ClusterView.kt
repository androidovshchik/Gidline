package ru.gidline.app.screen.catalog.map

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import org.jetbrains.anko.dip
import ru.gidline.app.R
import kotlin.math.max

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
        val padding = dip(10)
        id = R.id.amu_text
        setTextColor(Color.parseColor("#d18bc9"))
        setBackgroundResource(R.drawable.background_consulate)
        setPadding(padding, padding, padding, padding)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = max(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun hasOverlappingRendering() = false
}