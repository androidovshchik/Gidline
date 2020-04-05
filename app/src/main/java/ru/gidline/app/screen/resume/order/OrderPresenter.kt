package ru.gidline.app.screen.resume.order

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class OrderPresenter(context: Context) : BasePresenter<OrderContract.View>(context),
    OrderContract.Presenter