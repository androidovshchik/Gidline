package ru.gidline.app.screen.vacancy

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface VacancyContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}