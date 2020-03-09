package ru.gidline.app.screen.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.longToast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.*
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<P : IPresenter<*>> : Fragment(), IView, KodeinAware {

    override val kodein by closestKodein()

    protected abstract val presenter: P

    protected val args: Bundle
        get() = arguments ?: Bundle()

    protected val parentFragment: BaseFragment<*>?
        get() = getParentFragment() as? BaseFragment<*>

    override val topFragment: BaseFragment<*>?
        get() = childFragmentManager.topFragment as? BaseFragment<*>

    override fun showFragment(id: Int) {
        childFragmentManager.showFragment(id)
    }

    override fun hideFragment(id: Int) {
        childFragmentManager.hideFragment(id)
    }

    override fun addFragment(fragment: BaseFragment<*>) {
        childFragmentManager.addFragment(R.id.fl_container, fragment)
    }

    override fun putFragment(fragment: BaseFragment<*>) {
        childFragmentManager.putFragment(R.id.fl_container, fragment)
    }

    override fun popFragment(name: String?, immediate: Boolean) =
        childFragmentManager.popFragment(name, immediate)

    override fun onClick(v: View) {}

    override fun showMessage(text: String) {
        view?.snackbar(text)
    }

    override fun showError(e: Throwable) {
        context?.longToast(e.localizedMessage ?: e.toString())
    }

    inline fun <reified T> makeCallback(action: T.() -> Unit) {
        context?.makeCallback(action)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}