@file:Suppress("unused")

package ru.gidline.app.extension

import android.app.Activity

fun Activity.requestPermissions(requestCode: Int, vararg permissions: String) {
    if (isMarshmallowPlus()) {
        requestPermissions(permissions, requestCode)
    }
}