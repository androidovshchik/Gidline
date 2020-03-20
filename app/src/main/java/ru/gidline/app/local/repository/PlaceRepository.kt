package ru.gidline.app.local.repository

import android.content.Context
import de.siegmar.fastcsv.reader.CsvReader
import ru.gidline.app.local.model.Place

class PlaceRepository(private val csvReader: CsvReader) : BaseRepository<Place>() {

    private val places = mutableListOf<Place>()

    override fun getAll() = places

    fun getById(id: Int) = places.first { it.id == id }

    fun getByType(type: String) = places.filter { it.type == type }

    override fun initData(context: Context) {
        places.clear()
        context.assets.open("places.csv")
            .bufferedReader()
            .use { reader ->
                csvReader.parse(reader).use { parser ->
                    var i = 0
                    do {
                        parser.nextRow()?.let {
                            places.add(Place(++i, it))
                        } ?: break
                    } while (true)
                }
            }
    }
}