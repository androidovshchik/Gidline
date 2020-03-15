package ru.gidline.app.local.model

import de.siegmar.fastcsv.reader.CsvRow

class Place(val id: Int, row: CsvRow) {

    val type: String = row.getField(0)

    val country: String = row.getField(1)

    val locality: String = row.getField(2)

    val name: String = row.getField(3)

    val address: String = row.getField(4)

    val latitude = row.getField(5).toDouble()

    val longitude = row.getField(6).toDouble()

    val phones: String = row.getField(7)

    val schedule: String = row.getField(8)
}