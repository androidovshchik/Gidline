package ru.gidline.app.screen.search

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText
import ru.gidline.app.extension.activityCallback
import ru.gidline.app.screen.base.listener.IView

@SuppressLint("AppCompatCustomView")
class SearchView : EditText {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = android.R.attr.editTextStyle
    ) : super(
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

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            context.activityCallback<IView> {
                when (val topFragment = topFragment) {
                    is SearchContract.View -> {
                        topFragment.closePopup()
                    }
                }
            }
        }
        return super.onKeyPreIme(keyCode, event)
    }

    override fun hasOverlappingRendering() = false
}