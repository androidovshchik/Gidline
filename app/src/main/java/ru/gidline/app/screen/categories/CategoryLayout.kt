package ru.gidline.app.screen.categories

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.merge_category.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.use

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
        View.inflate(context, R.layout.merge_category, this)
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.CategoryLayout).use {
                getString(R.styleable.CategoryLayout_text)?.let {
                    tv_name.text = it
                }
            }
        }
    }

    override fun hasOverlappingRendering() = false
}