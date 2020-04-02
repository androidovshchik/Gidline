package ru.gidline.app.screen.base

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.SimpleArrayMap
import androidx.fragment.app.Fragment
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.*
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.presenterModule
import ru.gidline.app.screen.screenModule

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<P : IPresenter<*>> : AppCompatActivity(), IView, KodeinAware {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by Kodein.lazy {

        extend(parentKodein)

        import(presenterModule)

        import(screenModule)
    }

    protected abstract val presenter: P

    private val nestedFragments = SimpleArrayMap<Int, Fragment>()

    override val topFragment: IView?
        get() = supportFragmentManager.topFragment?.let {
            if (it is IView && it.view != null) {
                return it
            }
            null
        }

    override var isTouchable = true
        get() = window.attributes.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE == 0
        set(value) {
            val flag = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            if (value) {
                window.clearFlags(flag)
            } else {
                window.setFlags(flag, flag)
            }
            field = value
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T> findFragment(id: Int): T? {
        if (!nestedFragments.containsKey(id)) {
            nestedFragments.put(id, supportFragmentManager.findFragmentById(id))
        }
        return nestedFragments.get(id)?.let {
            if (it.view != null) {
                return it as? T
            }
            null
        }
    }

    override fun showFragment(id: Int) {
        supportFragmentManager.showFragment(id)
    }

    override fun hideFragment(id: Int) {
        supportFragmentManager.hideFragment(id)
    }

    override fun addFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.addFragment(R.id.fl_container, fragment)
    }

    override fun putFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.putFragment(R.id.fl_container, fragment)
    }

    override fun popFragment(name: String?, immediate: Boolean) =
        supportFragmentManager.popFragment(name, immediate)

    override fun showMessage(text: String) {
        toast(text)
    }

    override fun showError(e: Throwable) {
        longToast(e.localizedMessage ?: e.toString())
    }

    override fun onClick(v: View) {}

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        nestedFragments.clear()
        presenter.detachView()
        super.onDestroy()
    }
}