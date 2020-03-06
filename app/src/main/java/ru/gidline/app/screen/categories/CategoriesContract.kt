package ru.gidline.app.screen.categories

import ru.gidline.app.screen.base.listeners.IPresenter
import ru.gidline.app.screen.base.listeners.IView

interface CategoriesContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}