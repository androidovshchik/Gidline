package ru.gidline.app.screen.catalog.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView
import io.github.inflationx.calligraphy3.CalligraphyUtils
import org.jetbrains.anko.dip
import ru.gidline.app.R
import ru.gidline.app.local.model.Place
import kotlin.math.max

class ClusterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialTextView(context, attrs, defStyleAttr) {

    constructor(context: Context, type: String) : this(context) {
        when (type) {
            Place.CONSULATE -> {
                setTextColor(ContextCompat.getColor(context, R.color.colorConsulate))
                setBackgroundResource(R.drawable.background_consulate)
            }
            Place.MIGRATION -> {
                setTextColor(ContextCompat.getColor(context, R.color.colorMigration))
                setBackgroundResource(R.drawable.background_migration)
            }
            else -> {
            }
        }
    }

    init {
        init(attrs)
    }

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n")
    private fun init(attrs: AttributeSet?) {
        id = R.id.amu_text
        textSize = 17f
        gravity = Gravity.CENTER
        val padding = dip(8)
        setPadding(padding, padding, padding, padding)
        CalligraphyUtils.applyFontToTextView(
            this,
            Typeface.createFromAsset(context.assets, "font/Arial-Bold.ttf")
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = max(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun hasOverlappingRendering() = false
}