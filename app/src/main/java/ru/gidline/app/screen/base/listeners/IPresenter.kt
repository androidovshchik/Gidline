package ru.gidline.app.screen.base.listeners

interface IPresenter<V : IView> {

    fun attachView(view: V)

    fun detachView()
}