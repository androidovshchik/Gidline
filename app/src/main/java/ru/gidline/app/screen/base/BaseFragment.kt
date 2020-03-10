package ru.gidline.app.screen.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
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

    override val isTouchable: Boolean
        get() = activity?.window?.attributes?.flags?.and(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE) == 0

    override fun setTouchable(enable: Boolean) {
        val flag = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        if (enable) {
            activity?.window?.clearFlags(flag)
        } else {
            activity?.window?.setFlags(flag, flag)
        }
    }

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
        context?.toast(text)
    }

    override fun showError(e: Throwable) {
        context?.longToast(e.localizedMessage ?: e.toString())
    }

    inline fun <reified T> activityCallback(action: T.() -> Unit) {
        context?.activityCallback(action)
    }

    inline fun <reified T> stackCallback(action: T.() -> Unit) {
        childFragmentManager.topFragment?.let {
            if (it is T && it.view != null) {
                action(it)
            }
        }
    }

    inline fun <reified T> parentCallback(action: T.() -> Unit) {
        parentFragment?.let {
            if (it is T && it.view != null) {
                action(it)
            }
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}