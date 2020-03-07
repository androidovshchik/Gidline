package ru.gidline.app.screen.base

import android.content.Context
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import ru.gidline.app.screen.base.listener.IPresenter
import ru.gidline.app.screen.base.listener.IView
import timber.log.Timber
import java.lang.ref.WeakReference

@Suppress("MemberVisibilityCanBePrivate")
abstract class BasePresenter<V : IView>(context: Context) : IPresenter<V>, KodeinAware,
    CoroutineScope {

    override val kodein by closestKodein(context)

    protected lateinit var reference: WeakReference<V>

    protected val job = SupervisorJob()

    override fun attachView(view: V) {
        reference = WeakReference(view)
    }

    override fun detachView() {
        job.cancelChildren()
        reference.clear()
    }

    override val coroutineContext = Dispatchers.Main + job + CoroutineExceptionHandler { _, e ->
        Timber.e(e)
        if (e !is CancellationException) {
            reference.get()?.showError(e)
        }
    }
}