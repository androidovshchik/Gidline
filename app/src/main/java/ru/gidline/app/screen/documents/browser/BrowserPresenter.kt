package ru.gidline.app.screen.documents.browser

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class BrowserPresenter(context: Context) : BasePresenter<BrowserContract.View>(context),
    BrowserContract.Presenter