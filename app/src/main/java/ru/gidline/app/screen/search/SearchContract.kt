package ru.gidline.app.screen.search

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface SearchContract {

    interface View : IView {

        fun refreshData()

        fun hideSuggestion()
    }

    interface Presenter : IPresenter<View>
}