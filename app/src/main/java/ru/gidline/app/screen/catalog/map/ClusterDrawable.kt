package ru.gidline.app.screen.catalog.map

import android.graphics.*
import android.graphics.drawable.Drawable
import ru.gidline.app.local.model.Place
import kotlin.math.min

class ClusterDrawable(type: String, strokeSize: Float) : Drawable() {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = strokeSize
        color = when (type) {
            Place.CONSULATE -> Color.parseColor("#d18bc9")
            Place.MIGRATION -> Color.parseColor("#e1619e")
            else -> Color.TRANSPARENT
        }
    }

    private var radius = 0f

    override fun onBoundsChange(bounds: Rect) {
        radius = min(bounds.width(), bounds.height()) / 2f
        super.onBoundsChange(bounds)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(bounds.centerX().toFloat(), bounds.centerY().toFloat(), radius, paint)
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(bounds.centerX().toFloat(), bounds.centerY().toFloat(), radius, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT
}