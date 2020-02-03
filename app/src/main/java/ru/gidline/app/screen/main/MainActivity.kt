package ru.gidline.app.screen.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import coil.api.load
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseActivity
import ru.gidline.app.screen.filter.FilterFragment
import ru.gidline.app.screen.search.SearchFragment
import ru.gidline.app.screen.vacancy.VacancyFragment

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    override val presenter: MainPresenter by instance()

    private lateinit var filterFragment: FilterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        filterFragment = supportFragmentManager.findFragmentById(R.id.f_filter) as FilterFragment
        iv_background.load(R.drawable.background)
        ib_home.setOnClickListener(this)
        mb_action.setOnClickListener(this)
        hideFragment(R.id.f_filter)
        addFragment(SearchFragment.newInstance())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_home -> when (v.tag) {
                R.drawable.arrow_left -> {
                    topFragment.also {
                        if (it is SearchFragment) {
                            if (it.chipsPopup.isShowing) {
                                updateHome(R.drawable.hamburger)
                                it.hideSuggestion()
                                return
                            }
                        }
                    }
                    onBackPressed()
                }
            }
            R.id.mb_action -> if (filterFragment.isVisible) {
                filterFragment.saveFilter()
                topFragment.also {
                    if (it is SearchFragment) {
                        it.refreshData()
                    }
                }
                hideFragment(R.id.f_filter)
            } else when (topFragment) {
                is VacancyFragment -> {
                    popFragment(null, false)
                }
            }
        }
    }

    override fun updateHome(drawable: Int) {
        ib_home.apply {
            tag = drawable
            setImageResource(drawable)
        }
    }

    override fun updateAction(text: String?) {
        mb_action.also {
            if (text != null) {
                it.text = text
            }
            it.isVisible = text != null
        }
    }

    override fun onBackPressed() {
        if (filterFragment.isVisible) {
            hideFragment(R.id.f_filter)
        } else {
            super.onBackPressed()
        }
    }
}
