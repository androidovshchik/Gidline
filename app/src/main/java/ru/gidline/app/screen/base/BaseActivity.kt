package ru.gidline.app.screen.base

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
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

    override val isTouchable: Boolean
        get() = window.attributes.flags and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE == 0

    override fun setTouchable(enable: Boolean) {
        val flag = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        if (enable) {
            window.clearFlags(flag)
        } else {
            window.setFlags(flag, flag)
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

    inline fun <reified T> stackCallback(action: T.() -> Unit) {
        supportFragmentManager.topFragment?.let {
            if (it is T && it.view != null) {
                action(it)
            }
        }
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
        presenter.detachView()
        super.onDestroy()
    }
}