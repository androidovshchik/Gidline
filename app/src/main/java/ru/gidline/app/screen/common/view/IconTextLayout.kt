package ru.gidline.app.screen.common.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.merge_icon_text.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.use
import ru.gidline.app.screen.base.shape.ShapeRelativeLayout

class IconTextLayout : ShapeRelativeLayout, KodeinAware {

    override val kodein by closestKodein()

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n")
    override fun init(attrs: AttributeSet?) {
        super.init(attrs)
        View.inflate(context, R.layout.merge_icon_text, this)
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.IconTextLayout).use {
                iv_icon.setImageDrawable(getDrawable(R.styleable.IconTextLayout_icon))
                getString(R.styleable.IconTextLayout_text)?.let {
                    tv_name.text = it
                }
            }
        }
    }

    override fun hasOverlappingRendering() = false
}