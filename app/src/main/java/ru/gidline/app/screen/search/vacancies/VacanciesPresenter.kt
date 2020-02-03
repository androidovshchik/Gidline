package ru.gidline.app.screen.search.vacancies

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class VacanciesPresenter(context: Context) : BasePresenter<VacanciesContract.View>(context),
    VacanciesContract.Presenter