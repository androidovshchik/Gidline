package ru.gidline.app.screen.resume

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface ResumeContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}