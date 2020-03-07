package ru.gidline.app.screen.base

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.longToast
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

    override val topFragment: BaseFragment<*>?
        get() = supportFragmentManager.topFragment as? BaseFragment<*>

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

    override fun onClick(v: View) {}

    override fun showMessage(text: String) {
        contentView?.snackbar(text)
    }

    override fun showError(e: Throwable) {
        longToast(e.localizedMessage ?: e.toString())
    }

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