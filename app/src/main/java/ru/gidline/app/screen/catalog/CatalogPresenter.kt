package ru.gidline.app.screen.catalog

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class CatalogPresenter(context: Context) : BasePresenter<CatalogContract.View>(context),
    CatalogContract.Presenter