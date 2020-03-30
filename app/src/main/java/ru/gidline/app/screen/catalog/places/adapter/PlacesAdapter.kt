package ru.gidline.app.screen.catalog.places.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_place.view.*
import ru.gidline.app.R
import ru.gidline.app.local.model.Place
import ru.gidline.app.screen.base.BaseAdapter
import ru.gidline.app.screen.base.BaseHolder
import ru.gidline.app.screen.catalog.places.PlacesContract
import kotlin.math.min

@Suppress("MemberVisibilityCanBePrivate")
class PlacesAdapter(listener: PlacesContract.Recycler) :
    BaseAdapter<PlacesContract.Recycler, Place>(listener) {

    var iconId = 0

    var limited = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_place))
    }

    override fun getItemCount() = if (limited) min(3, items.size) else items.size

    inner class ViewHolder(itemView: View) : BaseHolder<Place>(itemView) {

        private val icon = itemView.iv_icon

        private val name = itemView.tv_name

        private val address = itemView.tv_address

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                reference?.get()?.onItemSelected(position, items[position])
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBindItem(position: Int, item: Place) {
            icon.setImageResource(iconId)
            name.text = item.name
            address.text = item.address
        }
    }
}