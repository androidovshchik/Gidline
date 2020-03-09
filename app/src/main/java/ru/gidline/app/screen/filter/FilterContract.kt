package ru.gidline.app.screen.filter

import android.widget.AdapterView
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface FilterContract {

    interface View : IView, AdapterView.OnItemSelectedListener

    interface Presenter : IPresenter<View>
}