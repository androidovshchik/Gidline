package ru.gidline.app.screen.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import coil.api.load
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.dip
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.statusBarHeight
import ru.gidline.app.extension.windowSize
import ru.gidline.app.local.BellRepository
import ru.gidline.app.screen.base.BaseActivity
import ru.gidline.app.screen.categories.CategoriesFragment
import ru.gidline.app.screen.common.ToastPopup
import ru.gidline.app.screen.filter.FilterFragment
import ru.gidline.app.screen.notifications.NotificationsFragment
import ru.gidline.app.screen.search.SearchFragment
import ru.gidline.app.screen.settings.SettingsFragment
import ru.gidline.app.screen.vacancy.VacancyFragment

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    override val presenter: MainPresenter by instance()

    private val bellRepository: BellRepository by instance()

    private val menuPopup: MenuPopup by instance()

    private val toastPopup: ToastPopup by instance(arg = "уведомлений нет")

    private lateinit var filterFragment: FilterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        filterFragment = supportFragmentManager.findFragmentById(R.id.f_filter) as FilterFragment
        iv_background.load(R.drawable.background)
        ib_home.setOnClickListener(this)
        ib_bell.setOnClickListener(this)
        ib_settings.setOnClickListener(this)
        ib_map.setOnClickListener(this)
        mb_action.setOnClickListener(this)
        supportFragmentManager.addOnBackStackChangedListener {
            when (val topFragment = topFragment) {
                is NotificationsFragment -> topFragment.refreshData()
            }
        }
        hideFragment(R.id.f_filter)
        addFragment(CategoriesFragment.newInstance())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_home -> {
                if (v.tag == R.drawable.arrow_left) {
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
                } else {
                    menuPopup.show(v)
                }
            }
            R.id.ib_bell -> {
                if (bellRepository.allCount > 0) {
                    putFragment(NotificationsFragment.newInstance())
                } else {
                    val top = statusBarHeight + resources.getDimension(R.dimen.toolbar_height)
                        .toInt() - dip(5)
                    val start = windowManager.windowSize.x - dip(180)
                    toastPopup.show(v, top, start)
                }
            }
            R.id.ib_settings -> {
                putFragment(SettingsFragment.newInstance())
            }
            R.id.mb_action -> {
                if (filterFragment.isVisible) {
                    filterFragment.saveFilter()
                    topFragment.also {
                        if (it is SearchFragment) {
                            it.refreshData()
                        }
                    }
                    hideFragment(R.id.f_filter)
                } else if (topFragment is VacancyFragment) {
                    popFragment(null, false)
                }
            }
        }
    }

    override fun setTitle(text: String) {
        tv_title.text = text
    }

    override fun notifyBell(all: Int, unread: Int) {
        iv_bell_daw.isVisible = unread > 0
        ib_bell.isVisible = all >= 0
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

    override fun toggleBottomNav(show: Boolean) {
        ll_bottom_nav.isVisible = show
    }

    override fun onBackPressed() {
        when {
            menuPopup.isShowing -> menuPopup.dismiss()
            filterFragment.isVisible -> hideFragment(R.id.f_filter)
            else -> super.onBackPressed()
        }
    }
}
