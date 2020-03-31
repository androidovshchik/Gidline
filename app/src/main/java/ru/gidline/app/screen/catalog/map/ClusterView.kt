package ru.gidline.app.screen.catalog.map

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import org.jetbrains.anko.dip
import ru.gidline.app.R
import ru.gidline.app.local.model.Place
import kotlin.math.max

@SuppressLint("AppCompatCustomView")
class ClusterView : TextView {

    constructor(context: Context, type: String) : this(context) {
        when (type) {
            Place.CONSULATE -> {
                setTextColor(Color.parseColor("#d18bc9"))
                setBackgroundResource(R.drawable.background_consulate)
            }
            Place.MIGRATION -> {
                setTextColor(Color.parseColor("#e1619e"))
                setBackgroundResource(R.drawable.background_migration)
            }
            else -> {
            }
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

    @SuppressLint("Recycle")
    private fun init(attrs: AttributeSet?) {
        id = R.id.amu_text
        textSize = 17f
        gravity = Gravity.CENTER
        val padding = dip(8)
        setPadding(padding, padding, padding, padding)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = max(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun hasOverlappingRendering() = false
}