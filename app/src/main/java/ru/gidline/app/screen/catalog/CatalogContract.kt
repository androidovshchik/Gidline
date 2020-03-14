package ru.gidline.app.screen.catalog

import com.google.android.material.tabs.TabLayout
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface CatalogContract {

    interface View : IView, TabLayout.OnTabSelectedListener

    interface Presenter : IPresenter<View>
}