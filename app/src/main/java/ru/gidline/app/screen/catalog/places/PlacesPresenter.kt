package ru.gidline.app.screen.catalog.places

import android.content.Context
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.gidline.app.screen.base.BasePresenter

class PlacesPresenter(context: Context) : BasePresenter<PlacesContract.View>(context),
    PlacesContract.Presenter {

    override fun awaitDelay() {
        job.cancelChildren()
        launch {
            delay(3000L)
            reference.get()?.updateDelayed()
        }
    }
}