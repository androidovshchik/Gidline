package ru.gidline.app.screen.filter

import android.widget.AdapterView
import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface FilterContract {

    interface View : IView, AdapterView.OnItemSelectedListener {

        fun saveFilter()
    }

    interface Presenter : IPresenter<View>
}