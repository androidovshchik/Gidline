package ru.gidline.app.screen.common

import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import ru.gidline.app.R
import ru.gidline.app.extension.statusBarHeight

class ToastPopup(text: String, context: Context) : PopupWindow(context) {

    private val topOffset = context.statusBarHeight

    init {
        inputMethodMode = INPUT_METHOD_NOT_NEEDED
        contentView = View.inflate(context, R.layout.popup_toast, null).also {
            (it as TextView).text = text
        }
    }

    fun show(anchor: View) {
        showAtLocation(anchor, Gravity.NO_GRAVITY, 0, topOffset)
        Handler().postDelayed({
            dismiss()
        }, 1000)
    }
}