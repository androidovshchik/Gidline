package ru.gidline.app.screen.catalog.map

import android.view.ViewTreeObserver
import com.google.android.gms.maps.OnMapReadyCallback
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.catalog.CatalogContract

interface MapContract {

    interface View : IView, OnMapReadyCallback, CatalogContract.Radar,
        ViewTreeObserver.OnGlobalLayoutListener {

        fun pointPlace(id: Int)
    }

    interface Presenter : IPresenter<View>
}