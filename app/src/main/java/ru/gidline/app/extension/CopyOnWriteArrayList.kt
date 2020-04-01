package ru.gidline.app.extension

import java.util.concurrent.CopyOnWriteArrayList

inline fun <T, R : Comparable<R>> CopyOnWriteArrayList<T>.sortBy(crossinline selector: (T) -> R?) {
    if (size > 1) {
        val list = ArrayList(this)
        list.sortBy(selector)
        clear()
        addAll(list)
    }
}