package ru.gidline.app.screen.search.vacancies

import ru.gidline.app.local.dto.Vacancy
import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IRecycler
import ru.gidline.app.screen.base.listeners.IView

interface VacanciesContract {

    interface View : IView, IRecycler<Vacancy> {

        fun refreshData(): Boolean
    }

    interface Presenter : IPresenter<View>
}