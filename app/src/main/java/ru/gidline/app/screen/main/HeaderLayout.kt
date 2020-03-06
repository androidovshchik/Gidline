package ru.gidline.app.screen.main

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.merge_header.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R

class HeaderLayout : RelativeLayout, KodeinAware {

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
    @Suppress("DEPRECATION")
    private fun init(attrs: AttributeSet?) {
        setBackgroundResource(R.drawable.header_background)
        View.inflate(context, R.layout.merge_header, this)
        iv_avatar.drawable.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        tv_name.text = "Хуршед"
        tv_surname.text = "Хасанов"
    }

    override fun hasOverlappingRendering() = false
}