package ru.gidline.app.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.sdk19.listeners.textChangedListener
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.makeCallback
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.main.MainContract
import ru.gidline.app.screen.search.f04.F04Fragment
import ru.gidline.app.screen.search.vacancies.VacanciesFragment

class SearchFragment : BaseFragment<SearchContract.Presenter>(), SearchContract.View {

    override val presenter: SearchPresenter by instance()

    private val searchFilter: SearchFilter by instance()

    private lateinit var vacanciesFragment: VacanciesFragment

    val chipsPopup: ChipsPopup by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vacanciesFragment =
            childFragmentManager.findFragmentById(R.id.f_vacancies) as VacanciesFragment
        et_search.setOnTouchListener { v, _ ->
            context?.makeCallback<MainContract.View> {
                updateHome(R.drawable.arrow_left)
            }
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
        ib_filter.setOnClickListener {
            hideSuggestion()
            et_search.clearFocus()
            context?.makeCallback<MainContract.View> {
                updateHome(R.drawable.hamburger)
                showFragment(R.id.f_filter)
            }
        }
        et_search.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSuggestion()
                context?.makeCallback<MainContract.View> {
                    updateHome(R.drawable.hamburger)
                }
                refreshData()
                true
            } else {
                false
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
        chipsPopup.dismiss()
    }

    override fun onDestroyView() {
        chipsPopup.dismiss()
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