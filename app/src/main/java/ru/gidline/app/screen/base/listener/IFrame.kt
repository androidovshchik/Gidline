package ru.gidline.app.screen.base.listener

import ru.gidline.app.screen.base.BaseFragment

interface IFrame : IView {

    val parentTopFragment: IFrame?

    fun parentShowFragment(id: Int)

    fun parentHideFragment(id: Int)

    fun parentAddFragment(fragment: BaseFragment<*>)

    fun parentPutFragment(fragment: BaseFragment<*>)

    fun parentPopFragment(name: String?, immediate: Boolean): Boolean
}