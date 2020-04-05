package ru.gidline.app.screen.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.collection.SimpleArrayMap
import androidx.fragment.app.Fragment
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import ru.gidline.app.R
import ru.gidline.app.extension.*
import ru.gidline.app.screen.base.listener.IFrame
import ru.gidline.app.screen.base.listener.IPresenter

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<P : IPresenter<*>> : Fragment(), IFrame, KodeinAware {

    override val kodein by closestKodein()

    protected abstract val presenter: P

    private val nestedFragments = SimpleArrayMap<Int, Fragment>()

    protected val args: Bundle
        get() = arguments ?: Bundle()

    override var isTouchable = true
        get() = activity?.window?.attributes?.flags?.and(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE) == 0
        set(value) {
            val flag = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            activity?.window?.apply {
                if (value) {
                    clearFlags(flag)
                } else {
                    setFlags(flag, flag)
                }
            }
            field = value
        }

    override val activityTopFragment: IFrame?
        get() = fragmentManager?.topFragment?.let {
            if (it is IFrame && it.view != null) {
                return it
            }
            null
        }

    override val topFragment: IFrame?
        get() = childFragmentManager.topFragment?.let {
            if (it is IFrame && it.view != null) {
                return it
            }
            null
        }

    override fun <T> activityFindFragment(id: Int): T? {
        TODO("Not yet implemented")
    }

    override fun activityShowFragment(id: Int) {
        fragmentManager?.showFragment(id)
    }

    override fun activityHideFragment(id: Int) {
        fragmentManager?.hideFragment(id)
    }

    override fun activityAddFragment(fragment: BaseFragment<*>) {
        fragmentManager?.addFragment(R.id.fl_container, fragment)
    }

    override fun activityPutFragment(fragment: BaseFragment<*>) {
        fragmentManager?.putFragment(R.id.fl_container, fragment)
    }

    override fun activityPopFragment(name: String?, immediate: Boolean) =
        fragmentManager?.popFragment(name, immediate) ?: false

    @Suppress("UNCHECKED_CAST")
    override fun <T> findFragment(id: Int): T? {
        if (!nestedFragments.containsKey(id)) {
            nestedFragments.put(id, childFragmentManager.findFragmentById(id))
        }
        return nestedFragments.get(id)?.let {
            if (it.view != null) {
                return it as? T
            }
            null
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

    inline fun <reified T> parentCallback(nullableView: Boolean = false, action: T.() -> Unit) {
        parentFragment?.let {
            if (it is T && (nullableView || it.view != null)) {
                action(it)
            }
        }
    }

    override fun onDestroy() {
        nestedFragments.clear()
        presenter.detachView()
        super.onDestroy()
    }
}