package ru.gidline.app.screen.main.categories

import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView

interface CategoriesContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}