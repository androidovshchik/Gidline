package ru.gidline.app.screen.notifications.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.dip

class NotificationsDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val vSpace = context.dip(10)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            left = 0
            right = 0
            bottom = vSpace
            top = if (parent.getChildAdapterPosition(view) == 0) vSpace else 0
        }
    }
}