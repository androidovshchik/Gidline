package ru.gidline.app.screen.main

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class MainPresenter(context: Context) : BasePresenter<MainContract.View>(context),
    MainContract.Presenter
