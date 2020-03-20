package ru.gidline.app.screen.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.merge_category.view.*
import org.jetbrains.anko.dip
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.use
import kotlin.math.roundToInt

class CategoryLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr), KodeinAware {

    override val kodein by closestKodein()

    init {
        init(attrs)
    }

    @SuppressLint("Recycle")
    private fun init(attrs: AttributeSet?) {
        radius = dip(10).toFloat()
        setCardBackgroundColor(Color.parseColor("#5cffffff"))
        View.inflate(context, R.layout.merge_category, this)
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.CategoryLayout).use {
                iv_icon.setImageDrawable(getDrawable(R.styleable.CategoryLayout_icon))
                getString(R.styleable.CategoryLayout_text)?.let {
                    tv_name.text = it
                }
                getDimension(R.styleable.CategoryLayout_offset, 0f).let {
                    (tv_name.layoutParams as MarginLayoutParams).marginStart = it.roundToInt()
                }
            }
        }
    }

    override fun hasOverlappingRendering() = false
}