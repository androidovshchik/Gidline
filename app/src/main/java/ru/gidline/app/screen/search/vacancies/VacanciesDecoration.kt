package ru.gidline.app.screen.search.vacancies

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.dip

class VacanciesDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val vMinSpace = context.dip(2)

    private val vSpace = context.dip(12)

    private val hSpace = context.dip(14)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            left = hSpace
            right = hSpace
            bottom = vSpace
            top = if (parent.getChildLayoutPosition(view) == 0) vMinSpace else 0
        }
    }
}