package ru.gidline.app.screen.base.listener

interface IRecycler<T> {

    fun onItemSelected(position: Int, item: T)
}