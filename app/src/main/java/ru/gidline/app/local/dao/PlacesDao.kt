package ru.gidline.app.local.dao

import androidx.room.Dao
import androidx.room.Query
import ru.gidline.app.local.entity.Place

@Dao
interface PlacesDao {

    @Query(
        """
        SELECT * FROM places
    """
    )
    fun getAll(): List<Place>
}