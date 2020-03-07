package ru.gidline.app.screen.search.vacancies.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_card.view.*
import org.jetbrains.anko.backgroundColor
import ru.gidline.app.R
import ru.gidline.app.local.dto.Vacancy
import ru.gidline.app.screen.base.BaseAdapter
import ru.gidline.app.screen.base.BaseHolder
import ru.gidline.app.screen.base.listener.IRecycler

@Suppress("MemberVisibilityCanBePrivate")
class VacanciesAdapter(listener: IRecycler<Vacancy>) : BaseAdapter<Vacancy>(listener) {

    val filteredItems = mutableListOf<Vacancy>()

    override fun onBindViewHolder(holder: BaseHolder<Vacancy>, position: Int) {
        holder.onBindItem(position, filteredItems[position])
    }

    override fun getItemCount() = filteredItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_card))
    }

    inner class ViewHolder(itemView: View) : BaseHolder<Vacancy>(itemView) {

        private val card = itemView.mcv_vacancy

        private val date = itemView.tv_date

        private val fire = itemView.iv_fire

        private val vacancy = itemView.tv_vacancy

        private val paymentTime = itemView.tv_payment_time

        private val city = itemView.tv_city

        private val place = itemView.tv_place

        private val patent = itemView.iv_patent

        private val form = itemView.tv_form

        init {
            card.setOnClickListener {
                reference?.get()?.onItemSelected(adapterPosition, filteredItems[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBindItem(position: Int, item: Vacancy) {
            card.setCardBackgroundColor(
                ContextCompat.getColor(
                    appContext,
                    if (item.quickly) R.color.colorCardFire else R.color.colorCardNormal
                )
            )
            date.text = item.date
            fire.isVisible = item.quickly
            vacancy.text = item.vacancy
            paymentTime.text = "${item.payment} ${item.perTime}"
            city.text = item.city
            place.text = item.place
            patent.isVisible = item.hasPatent
            form.apply {
                backgroundColor = item.color
                text = item.form
            }
        }
    }
}