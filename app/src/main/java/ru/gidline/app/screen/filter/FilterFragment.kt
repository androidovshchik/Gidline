package ru.gidline.app.screen.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ScrollView
import kotlinx.android.synthetic.main.fragment_filter.*
import org.jetbrains.anko.sdk19.listeners.onSeekBarChangeListener
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.filter.view.RadioButton
import ru.gidline.app.screen.filter.view.ToggleView
import ru.gidline.app.screen.main.MainContract
import ru.gidline.app.screen.search.SearchFilter
import kotlin.math.round

class FilterFragment : BaseFragment<FilterContract.Presenter>(), FilterContract.View {

    override val presenter: FilterPresenter by instance()

    private val regionAdapter: ArrayAdapter<String> by instance(arg = R.layout.item_spinner)

    private val cityAdapter: ArrayAdapter<String> by instance(arg = R.layout.item_spinner)

    private val searchFilter: SearchFilter by instance()

    private val calculator = Calculator()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_filter, root, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ib_close.setOnClickListener(this)
        tv_per_month.setOnClickListener(this)
        tv_per_item.setOnClickListener(this)
        tv_per_hour.setOnClickListener(this)
        sb_payment.onSeekBarChangeListener {
            onProgressChanged { _, progress, _ ->
                calculator.progress = progress
                updateProgress()
            }
        }
        s_region.also {
            it.adapter = regionAdapter.apply {
                clear()
                add("")
                add("Москва и Московская обл.")
            }
            it.onItemSelectedListener = this
        }
        s_city.also {
            it.adapter = cityAdapter
        }
        rb1.setOnClickListener(this)
        rb2.setOnClickListener(this)
        rb3.setOnClickListener(this)
        rb4.setOnClickListener(this)
        ib_checkbox1.setOnClickListener(this)
        ib_checkbox2.setOnClickListener(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) {
            makeCallback<MainContract.View> {
                updateAction(null)
            }
        } else {
            makeCallback<MainContract.View> {
                updateAction("Применить")
            }
            updateFilter()
            (view as ScrollView).scrollY = 0
        }
    }

    private fun updateFilter() {
        calculator.setFrom(searchFilter.calculator)
        updatePayment()
        val region = regionAdapter.getPosition(searchFilter.region.orEmpty())
        s_region.setSelection(region, false)
        updateCity(region, searchFilter.city)
        updateForm(searchFilter.form)
        updateCheckboxes(searchFilter.residence, searchFilter.freeFeed)
    }

    override fun saveFilter() {
        searchFilter.calculator.setFrom(calculator)
        searchFilter.region = s_region.selectedItem?.toString()?.ifEmpty { null }
        searchFilter.city = s_city.selectedItem?.toString()?.ifEmpty { null }
        searchFilter.form = when {
            rb1.isChecked -> rb1.tag.toString().toInt()
            rb2.isChecked -> rb2.tag.toString().toInt()
            rb3.isChecked -> rb3.tag.toString().toInt()
            rb4.isChecked -> rb4.tag.toString().toInt()
            else -> null
        }
        searchFilter.residence = ib_checkbox1.isChecked
        searchFilter.freeFeed = ib_checkbox2.isChecked
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_close -> {
                makeCallback<IView> {
                    hideFragment(R.id.f_filter)
                }
            }
            R.id.tv_per_month, R.id.tv_per_item, R.id.tv_per_hour -> {
                calculator.perTime =
                    if ((v as ToggleView).isChecked) null else v.tag.toString().toInt()
                updatePayment()
            }
            R.id.rb1, R.id.rb2, R.id.rb3, R.id.rb4 -> {
                updateForm(if ((v as RadioButton).isChecked) null else v.tag.toString().toInt())
            }
            R.id.ib_checkbox1 -> {
                updateCheckboxes(!ib_checkbox1.isChecked, ib_checkbox2.isChecked)
            }
            R.id.ib_checkbox2 -> {
                updateCheckboxes(ib_checkbox1.isChecked, !ib_checkbox2.isChecked)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.s_region -> {
                updateCity(position, null)
            }
        }
    }

    private fun updatePayment() = calculator.also {
        tv_per_month.isChecked = it.perTime == 0
        tv_per_item.isChecked = it.perTime == 1
        tv_per_hour.isChecked = it.perTime == 2
        sb_payment.apply {
            val prevMax = max
            val prevProgress = progress
            max = (it.maxPayment - it.minPayment) / it.payStep
            progress = if (it.perTime != null) {
                round(prevProgress * max.toFloat() / prevMax).toInt()
            } else 0
        }
        updateProgress()
    }

    @SuppressLint("SetTextI18n")
    private fun updateProgress() {
        tv_payment.text = if (calculator.perTime != null && calculator.progress > 0) {
            "${calculator.payment} РУБ."
        } else "не важно"
    }

    private fun updateCity(regionPosition: Int, city: String?) {
        cityAdapter.apply {
            clear()
            if (regionPosition > 0) {
                add("Москва")
            }
            notifyDataSetChanged()
        }
        s_city.setSelection(cityAdapter.getPosition(city), false)
    }

    private fun updateForm(form: Int?) {
        rb1.isChecked = form == 0
        rb2.isChecked = form == 1
        rb3.isChecked = form == 2
        rb4.isChecked = form == 3
    }

    private fun updateCheckboxes(residence: Boolean, freeFeed: Boolean) {
        ib_checkbox1.isChecked = residence
        ib_checkbox2.isChecked = freeFeed
    }

    companion object {

        fun newInstance(): FilterFragment {
            return FilterFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}