package ru.gidline.app.screen.main.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import coil.api.load
import coil.transform.CircleCropTransformation
import kotlinx.android.synthetic.main.merge_header.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.Preferences

class HeaderLayout : RelativeLayout, KodeinAware {

    override val kodein by closestKodein()

    private val preferences: Preferences by instance()

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
        setBackgroundResource(R.drawable.header_background)
        View.inflate(context, R.layout.merge_header, this)
        tv_name.text = "Хуршед"
        tv_surname.text = "Хасанов"
        updateAvatar()
    }

    @Suppress("DEPRECATION")
    fun updateAvatar() {
        iv_avatar.load(Uri.parse("file://${preferences.avatarPath}")) {
            error(resources.getDrawable(preferences.genderDrawable).mutate().apply {
                setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            })
            transformations(CircleCropTransformation())
        }
    }

    override fun hasOverlappingRendering() = false
}