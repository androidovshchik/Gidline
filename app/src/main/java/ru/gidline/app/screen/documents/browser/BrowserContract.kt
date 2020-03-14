package ru.gidline.app.screen.documents.browser

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface BrowserContract {

    interface View : IView {

        fun onProgress(progress: Int)
    }

    interface Presenter : IPresenter<View>
}