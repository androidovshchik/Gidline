package ru.gidline.app.screen.search

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface SearchContract {

    interface View : IView {

        fun refreshData()

        fun hideSuggestion()
    }

    interface Presenter : IPresenter<View>
}