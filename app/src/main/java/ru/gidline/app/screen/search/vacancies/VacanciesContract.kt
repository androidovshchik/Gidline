package ru.gidline.app.screen.search.vacancies

import ru.gidline.app.local.model.Vacancy
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IRecycler
import ru.gidline.app.screen.base.listener.IView

interface VacanciesContract {

    interface View : IView, Recycler {

        fun refreshData(): Boolean
    }

    interface Recycler : IRecycler<Vacancy>

    interface Presenter : IPresenter<View>
}