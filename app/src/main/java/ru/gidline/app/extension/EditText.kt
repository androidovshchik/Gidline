@file:Suppress("unused")

package ru.gidline.app.extension

import android.widget.EditText

fun EditText.setTextSelection(text: CharSequence?) {
    (text ?: "").let {
        setText(it)
        setSelection(it.length)
    }
}