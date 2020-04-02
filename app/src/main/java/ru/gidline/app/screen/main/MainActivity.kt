package ru.gidline.app.screen.main

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import coil.api.load
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.statusBarHeight
import ru.gidline.app.extension.windowSize
import ru.gidline.app.local.repository.BellRepository
import ru.gidline.app.screen.base.BaseActivity
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.common.ToastPopup
import ru.gidline.app.screen.documents.DocumentsContract
import ru.gidline.app.screen.main.categories.CategoriesContract
import ru.gidline.app.screen.main.categories.CategoriesFragment
import ru.gidline.app.screen.notifications.NotificationsContract
import ru.gidline.app.screen.notifications.NotificationsFragment
import ru.gidline.app.screen.search.SearchContract
import ru.gidline.app.screen.settings.SettingsContract

class MainActivity : BaseActivity<MainContract.Presenter>(), MainContract.View {

    override val presenter: MainPresenter by instance()

    private val bellRepository: BellRepository by instance()

    private val menuPopup: MenuPopup by instance()

    private val toastPopup: ToastPopup by instance(arg = "уведомлений нет")

    private val lifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            when (f) {
                is CategoriesContract.View -> notifyBell(-1)
                is SettingsContract.View -> window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                is CatalogContract.View -> ib_filter.isVisible = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv_background.load(R.drawable.background)
        ib_home.setOnClickListener(this)
        ib_bell.setOnClickListener(this)
        ib_filter.setOnClickListener(this)
        supportFragmentManager.registerFragmentLifecycleCallbacks(lifecycleCallbacks, true)
        supportFragmentManager.addOnBackStackChangedListener {
            when (val topFragment = topFragment) {
                is CategoriesContract.View -> onCategoriesEntry()
                is NotificationsContract.View -> {
                    updateHome(R.drawable.arrow_left)
                    setTitle("Уведомление")
                    topFragment.refreshData()
                }
                is SearchContract.View -> {
                    updateHome(R.drawable.arrow_left)
                    setTitle("Поиск работы")
                    notifyBell(-1)
                }
                is DocumentsContract.View -> {
                    updateHome(R.drawable.arrow_left)
                    setTitle("Проверка документов")
                }
                is SettingsContract.View -> {
                    updateHome(R.drawable.arrow_left)
                    setTitle("Настройка")
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                }
                is CatalogContract.View -> {
                    updateHome(R.drawable.arrow_left)
                    setTitle("Карта справочник")
                    notifyBell(-1)
                    ib_filter.isVisible = true
                }
            }
        }
        addFragment(CategoriesFragment.newInstance())
        onCategoriesEntry()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_home -> {
                if (v.tag == R.drawable.arrow_left) {
                    when (val topFragment = topFragment) {
                        is SearchContract.View -> {
                            if (topFragment.hasPopup) {
                                topFragment.hideSuggestion()
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
            R.id.ib_filter -> {
                when (val topFragment = topFragment) {
                    is CatalogContract.View -> {
                        topFragment.showFilter()
                    }
                }
            }
        }
    }

    private fun updateHome(drawable: Int) {
        ib_home.apply {
            tag = drawable
            setImageResource(drawable)
        }
    }

    override fun setTitle(text: String) {
        tv_title.text = text
    }

    private fun notifyBell(all: Int, unread: Int = 0) {
        ib_bell.isVisible = all >= 0
        iv_bell_daw.isVisible = unread > 0
    }

    private fun onCategoriesEntry() {
        updateHome(R.drawable.ic_hamburger)
        setTitle(getString(R.string.app_name))
        notifyBell(bellRepository.allCount, bellRepository.unreadCount)
    }

    override fun onBackPressed() {
        when {
            menuPopup.isShowing -> menuPopup.dismiss()
            (topFragment as? SearchContract.View)?.closeFilter() == true -> return
            else -> super.onBackPressed()
        }
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(lifecycleCallbacks)
        super.onDestroy()
    }
}
