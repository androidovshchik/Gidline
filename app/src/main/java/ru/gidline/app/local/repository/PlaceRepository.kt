package ru.gidline.app.local.repository

import android.content.Context
import de.siegmar.fastcsv.reader.CsvReader
import ru.gidline.app.local.model.Place

class PlaceRepository : BaseRepository<Place>() {

    private val places = mutableListOf<Place>()

    override fun getAll() = places

    override fun initData(context: Context) {
        places.clear()
        context.assets.open("places.csv")
            .bufferedReader()
            .use { reader ->
                CsvReader().parse(reader).use { parser ->
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