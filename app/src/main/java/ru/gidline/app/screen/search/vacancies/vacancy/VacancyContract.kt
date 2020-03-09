package ru.gidline.app.screen.search.vacancies.vacancy

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface VacancyContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}