package ru.gidline.app.screen.catalog.places.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import ru.gidline.app.R
import ru.gidline.app.local.model.Vacancy
import ru.gidline.app.screen.base.BaseAdapter
import ru.gidline.app.screen.base.BaseHolder
import ru.gidline.app.screen.catalog.places.PlacesContract

@Suppress("MemberVisibilityCanBePrivate")
class PlacesAdapter(listener: PlacesContract.Recycler) :
    BaseAdapter<PlacesContract.Recycler, Vacancy>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_card))
    }

    inner class ViewHolder(itemView: View) : BaseHolder<Vacancy>(itemView) {

        @SuppressLint("SetTextI18n")
        override fun onBindItem(position: Int, item: Vacancy) {

        }
    }
}