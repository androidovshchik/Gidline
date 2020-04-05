package ru.gidline.app.screen.base.listener

import ru.gidline.app.screen.base.BaseFragment

interface IFrame : IView {

    val activityTopFragment: IFrame?

    fun activityShowFragment(id: Int)

    fun activityHideFragment(id: Int)

    fun activityAddFragment(fragment: BaseFragment<*>)

    fun activityPutFragment(fragment: BaseFragment<*>)

    fun activityPopFragment(name: String?, immediate: Boolean): Boolean
}