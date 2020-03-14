package ru.gidline.app.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "places"
)
class Place {

    @PrimaryKey
    @ColumnInfo(name = "p_id")
    var id: Long? = null

    @ColumnInfo(name = "p_type")
    lateinit var type: String

    @ColumnInfo(name = "p_country")
    lateinit var country: String

    @ColumnInfo(name = "p_locality")
    var locality: String? = null

    @ColumnInfo(name = "p_name")
    lateinit var name: String

    @ColumnInfo(name = "p_address")
    lateinit var address: String

    @ColumnInfo(name = "p_phones")
    lateinit var phones: String

    @ColumnInfo(name = "p_email")
    var email: String? = null

    @ColumnInfo(name = "p_site")
    var site: String? = null

    @ColumnInfo(name = "p_schedule")
    lateinit var schedule: String

    @ColumnInfo(name = "p_comment")
    var comment: String? = null
}