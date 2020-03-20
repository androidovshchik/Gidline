package ru.gidline.app.screen.catalog.places

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.merge_places.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.use

class PlacesLayout : RelativeLayout, KodeinAware {

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
        View.inflate(context, R.layout.merge_places, this)
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.PlacesLayout).use {
                getString(R.styleable.PlacesLayout_text)?.let {
                    tv_name.text = it
                }
            }
        }
    }

    override fun hasOverlappingRendering() = false
}