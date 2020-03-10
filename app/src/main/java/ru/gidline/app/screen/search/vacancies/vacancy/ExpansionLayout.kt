package ru.gidline.app.screen.search.vacancies.vacancy

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.merge_accordion.view.*
import org.jetbrains.anko.dip
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.use
import ru.gidline.app.local.model.Vacancy

class ExpansionLayout : LinearLayout, KodeinAware {

    override val kodein by closestKodein()

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
        orientation = VERTICAL
        setPadding(dip(10), dip(10), dip(10), 0)
        View.inflate(context, R.layout.merge_accordion, this)
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.ExpansionLayout).use {
                getString(R.styleable.ExpansionLayout_text)?.let {
                    tv_name.text = it
                }
            }
        }
        ib_arrow.setOnClickListener {
            if (tv_text.isVisible) {
                ib_arrow.setImageResource(R.drawable.arrow_right)
                tv_text.isVisible = false
            } else {
                ib_arrow.setImageResource(R.drawable.arrow_down)
                tv_text.isVisible = true
            }
        }
    }

    fun updateText(vacancy: Vacancy) = context.run {
        tv_text.text = when (tv_name.text) {
            getString(R.string.accordion_offer) -> vacancy.offer
            getString(R.string.accordion_responsibility) -> vacancy.responsibility
            getString(R.string.accordion_requirement) -> vacancy.requirement
            else -> ""
        }
    }

    override fun hasOverlappingRendering() = false
}