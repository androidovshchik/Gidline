@file:Suppress("unused")

package ru.gidline.app.screen.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.layoutInflater
import ru.gidline.app.screen.base.listeners.IRecycler
import java.lang.ref.WeakReference

abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBindItem(position: Int, item: T)

    val appContext: Context
        get() = itemView.context.applicationContext
}

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseHolder<T>> {

    val items = mutableListOf<T>()

    protected var reference: WeakReference<IRecycler<T>>? = null

    constructor()

    constructor(listener: IRecycler<T>) {
        setListener(listener)
    }

    /**
     * It is assumed that this will be called one time or never
     */
    fun setListener(listener: IRecycler<T>) {
        reference = WeakReference(listener)
    }

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.onBindItem(position, items[position])
    }

    override fun getItemCount() = items.size

    protected fun ViewGroup.inflate(@LayoutRes layout: Int): View {
        return context.layoutInflater.inflate(layout, this, false)
    }
}