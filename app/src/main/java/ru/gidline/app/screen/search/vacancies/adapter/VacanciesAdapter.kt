package ru.gidline.app.screen.search.vacancies.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.item_vacancy.view.*
import ru.gidline.app.R
import ru.gidline.app.local.model.Vacancy
import ru.gidline.app.screen.base.BaseAdapter
import ru.gidline.app.screen.base.BaseHolder
import ru.gidline.app.screen.search.vacancies.VacanciesContract

@Suppress("MemberVisibilityCanBePrivate")
class VacanciesAdapter(listener: VacanciesContract.Recycler) :
    BaseAdapter<VacanciesContract.Recycler, Vacancy>(listener) {

    val filteredItems = mutableListOf<Vacancy>()

    override fun onBindViewHolder(holder: BaseHolder<Vacancy>, position: Int) {
        holder.onBindItem(position, filteredItems[position])
    }

    override fun getItemCount() = filteredItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_vacancy))
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
            card.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindItem(position: Int, item: Vacancy) {
            card.setCardBackgroundColor(
                ContextCompat.getColor(
                    appContext,
                    if (item.quickly) R.color.colorVacancyFire else R.color.colorVacancyNormal
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
                setBackgroundColor(item.color)
                text = item.form
            }
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.mcv_vacancy -> {
                    val position = adapterPosition
                    reference?.get()?.onItemSelected(position, filteredItems[position])
                }
            }
        }
    }
}