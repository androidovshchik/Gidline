package ru.gidline.app.screen.notifications.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.chauthai.swipereveallayout.ViewBinderHelper
import kotlinx.android.synthetic.main.item_notification.view.*
import ru.gidline.app.R
import ru.gidline.app.local.model.Bell
import ru.gidline.app.screen.base.BaseAdapter
import ru.gidline.app.screen.base.BaseHolder
import ru.gidline.app.screen.notifications.NotificationsContract

@Suppress("MemberVisibilityCanBePrivate")
class NotificationsAdapter(listener: NotificationsContract.Recycler) :
    BaseAdapter<NotificationsContract.Recycler, Bell>(listener) {

    private val binderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_notification))
    }

    inner class ViewHolder(itemView: View) : BaseHolder<Bell>(itemView) {

        private val notification = itemView.srl_notification

        private val delete = itemView.ib_delete

        private val card = itemView.mcv_notification

        private val strip = itemView.v_strip

        private val text = itemView.tv_text

        private val subtext = itemView.tv_subtext

        private val date = itemView.tv_date

        init {
            delete.setOnClickListener(this)
            card.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindItem(position: Int, item: Bell) {
            binderHelper.bind(notification, item.id.toString())
            strip.setBackgroundColor(if (item.unread) Color.parseColor("#d18bc9") else Color.TRANSPARENT)
            text.text = item.title
            subtext.text = item.subtitle
            date.text = item.date
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.ib_delete -> {
                    val position = adapterPosition
                    val item = items.removeAt(position)
                    reference?.get()?.onItemDeleted(item.id)
                }
                R.id.mcv_notification -> {
                    val position = adapterPosition
                    reference?.get()?.onItemSelected(position, items[position])
                }
            }
        }
    }
}