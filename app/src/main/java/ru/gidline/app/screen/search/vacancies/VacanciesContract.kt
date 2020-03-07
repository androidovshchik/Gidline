package ru.gidline.app.screen.search.vacancies

import ru.gidline.app.local.dto.Vacancy
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IRecycler
import ru.gidline.app.screen.base.listener.IView

interface VacanciesContract {

    interface View : IView, IRecycler<Vacancy> {

        fun refreshData(): Boolean
    }

    interface Presenter : IPresenter<View>
}