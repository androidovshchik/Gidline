package ru.gidline.app.screen.search

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class SearchPresenter(context: Context) : BasePresenter<SearchContract.View>(context),
    SearchContract.Presenter