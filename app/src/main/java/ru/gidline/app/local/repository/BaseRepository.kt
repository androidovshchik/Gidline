package ru.gidline.app.local.repository

import android.content.Context

abstract class BaseRepository<T> {

    abstract fun getAll(): List<T>

    abstract fun initData(context: Context)
}