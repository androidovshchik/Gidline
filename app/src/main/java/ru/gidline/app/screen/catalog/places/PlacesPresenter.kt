package ru.gidline.app.screen.catalog.places

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class PlacesPresenter(context: Context) : BasePresenter<PlacesContract.View>(context),
    PlacesContract.Presenter