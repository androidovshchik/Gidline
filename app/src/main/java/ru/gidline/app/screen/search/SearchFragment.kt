package ru.gidline.app.screen.search

import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import coil.api.load
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.sdk19.listeners.textChangedListener
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.search.f04.F04Fragment
import ru.gidline.app.screen.search.filter.FilterFragment
import ru.gidline.app.screen.search.vacancies.VacanciesFragment

class SearchFragment : BaseFragment<SearchContract.Presenter>(), SearchContract.View {

    override val presenter: SearchPresenter by instance()

    val searchFilter: SearchFilter by instance()

    private val chipsPopup: ChipsPopup by instance()

    private lateinit var filterFragment: FilterFragment

    private lateinit var vacanciesFragment: VacanciesFragment

    override val hasPopup: Boolean
        get() = chipsPopup.isShowing

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vacanciesFragment =
            childFragmentManager.findFragmentById(R.id.f_vacancies) as VacanciesFragment
        filterFragment = childFragmentManager.findFragmentById(R.id.f_filter) as FilterFragment
        iv_background.apply {
            load(R.drawable.background)
            imageMatrix = Matrix().apply {
                setTranslate(0f, 0f - resources.getDimension(R.dimen.toolbar_height))
            }
        }
        et_search.setOnTouchListener { v, _ ->
            chipsPopup.show(v)
            false
        }
        et_search.textChangedListener {
            afterTextChanged {
                val text = it.toString().trim()
                searchFilter.text = text.ifEmpty { null }
                chipsPopup.filter(text)
            }
        }
        ib_filter.setOnClickListener(this)
        et_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSuggestion()
                refreshData()
                true
            } else {
                false
            }
        }
        closeFilter()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_filter -> {
                hideSuggestion()
                showFragment(R.id.f_filter)
            }
        }
    }

    override fun refreshData() {
        hideFragment(R.id.f_vacancies)
        popFragment(null, true)
        if (vacanciesFragment.refreshData()) {
            showFragment(R.id.f_vacancies)
        } else {
            addFragment(F04Fragment.newInstance())
        }
    }

    override fun hideSuggestion() {
        context?.inputMethodManager?.hideSoftInputFromWindow(et_search.windowToken, 0)
        et_search.clearFocus()
        closePopup()
    }

    override fun closeFilter(): Boolean {
        if (filterFragment.isVisible) {
            hideFragment(R.id.f_filter)
            return true
        }
        return false
    }

    override fun closePopup() {
        chipsPopup.dismiss()
    }

    override fun onDestroyView() {
        closePopup()
        super.onDestroyView()
    }

    companion object {

        fun newInstance(): SearchFragment {
            return SearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}