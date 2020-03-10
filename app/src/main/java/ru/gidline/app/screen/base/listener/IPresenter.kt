package ru.gidline.app.screen.base.listener

interface IPresenter<V : IView> {

    fun attachView(view: V)

    fun detachView()
}