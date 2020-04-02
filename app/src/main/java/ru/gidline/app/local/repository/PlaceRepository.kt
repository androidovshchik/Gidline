package ru.gidline.app.local.repository

import android.content.Context
import de.siegmar.fastcsv.reader.CsvReader
import ru.gidline.app.local.model.Place
import java.util.concurrent.CopyOnWriteArrayList

class PlaceRepository(private val csvReader: CsvReader) : BaseRepository<Place>() {

    private val places = CopyOnWriteArrayList<Place>()

    override fun getAll() = places

    fun getById(id: Int): Place = places.first { it.id == id }

    fun getByType(type: String?) = places.filter { type.equals(it.type, true) }

    override fun initData(context: Context) {
        places.clear()
        context.assets.open("places.csv")
            .bufferedReader()
            .use { reader ->
                csvReader.parse(reader).use { parser ->
                    var i = 0
                    var z = 10f
                    do {
                        parser.nextRow()?.let {
                            places.add(Place(++i, it, z))
                            z += 0.001f
                        } ?: break
                    } while (true)
                }
            }
    }
}