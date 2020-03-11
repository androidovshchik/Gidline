package ru.gidline.app.screen.common

import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.TextView
import ru.gidline.app.R
import ru.gidline.app.screen.base.BasePopup

class ToastPopup(text: String, context: Context) : BasePopup(context) {

    init {
        inputMethodMode = INPUT_METHOD_NOT_NEEDED
        setBackgroundDrawable(null)
        contentView = View.inflate(context, R.layout.popup_toast, null).also {
            (it as TextView).text = text
            it.measureSize()
            width = it.measuredWidth
            height = it.measuredHeight
        }
    }

    fun show(anchor: View, top: Int, start: Int) {
        if (!isShowing) {
            showAtLocation(anchor, Gravity.NO_GRAVITY, start, top)
            Handler().postDelayed({
                dismiss()
            }, 1000)
        }
    }
}