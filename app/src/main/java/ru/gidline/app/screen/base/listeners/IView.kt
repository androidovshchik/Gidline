package ru.gidline.app.screen.base.listeners

import android.view.View
import ru.gidline.app.screen.base.BaseFragment

interface IView : View.OnClickListener {

    val topFragment: BaseFragment<*>?

    fun showFragment(id: Int)

    fun hideFragment(id: Int)

    fun addFragment(fragment: BaseFragment<*>)

    fun putFragment(fragment: BaseFragment<*>)

    fun popFragment(name: String?, immediate: Boolean): Boolean

    fun showMessage(text: String)

    fun showError(e: Throwable)
}