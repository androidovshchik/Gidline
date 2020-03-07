package ru.gidline.app.screen.notifications.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.chauthai.swipereveallayout.ViewBinderHelper
import kotlinx.android.synthetic.main.item_notification.view.*
import ru.gidline.app.R
import ru.gidline.app.local.dto.Bell
import ru.gidline.app.screen.base.BaseAdapter
import ru.gidline.app.screen.base.BaseHolder
import ru.gidline.app.screen.notifications.NotificationsContract

@Suppress("MemberVisibilityCanBePrivate")
class NotificationsAdapter(listener: NotificationsContract.Recycler) :
    BaseAdapter<NotificationsContract.Recycler, Bell>(listener) {

    private val viewBinderHelper = ViewBinderHelper()

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
            delete.setOnClickListener {
                val position = adapterPosition
                val item = items.removeAt(position)
                notifyDataSetChanged()
                reference?.get()?.onItemDeleted(item.id)
            }
            card.setOnClickListener {
                val position = adapterPosition
                reference?.get()?.onItemSelected(position, items[position])
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBindItem(position: Int, item: Bell) {
            viewBinderHelper.bind(notification, item.id.toString())
            strip.isVisible = item.unread
            text.text = item.title
            subtext.text = item.subtitle
            date.text = item.date
        }
    }
}