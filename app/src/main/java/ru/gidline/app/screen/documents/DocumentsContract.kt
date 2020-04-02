package ru.gidline.app.screen.documents

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface DocumentsContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}