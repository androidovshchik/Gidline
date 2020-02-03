package ru.gidline.app.screen.vacancy

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class VacancyPresenter(context: Context) : BasePresenter<VacancyContract.View>(context),
    VacancyContract.Presenter