package ru.gidline.app.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gidline.app.local.dao.BaseDao
import ru.gidline.app.local.dao.PlacesDao
import ru.gidline.app.local.entity.Place

@Database(
    entities = [
        Place::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun baseDao(): BaseDao

    abstract fun placesDao(): PlacesDao
}