package ru.gidline.app.screen.search

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface SearchContract {

    interface View : IView {

        val hasPopup: Boolean

        val searchFilter: SearchFilter

        fun refreshData()

        fun changeSearch(text: String)

        fun hideSuggestion()

        fun closeFilter(): Boolean

        fun closePopup()
    }

    interface Presenter : IPresenter<View>
}