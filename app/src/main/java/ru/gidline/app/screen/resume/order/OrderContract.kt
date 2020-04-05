package ru.gidline.app.screen.resume.order

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface OrderContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}