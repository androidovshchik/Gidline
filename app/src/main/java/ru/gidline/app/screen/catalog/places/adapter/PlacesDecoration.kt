package ru.gidline.app.screen.catalog.places.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.dip

class PlacesDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val bottomSpace = context.dip(6)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom =
            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) bottomSpace else 0
    }
}