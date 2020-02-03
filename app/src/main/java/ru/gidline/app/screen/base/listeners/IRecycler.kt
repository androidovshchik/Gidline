package ru.gidline.app.screen.base.listeners

interface IRecycler<T> {

    fun onItemSelected(position: Int, item: T)
}