package ru.gidline.app.local

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.siegmar.fastcsv.reader.CsvReader
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.gidline.app.local.repository.BellRepository
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.local.repository.VacancyRepository

val localModule = Kodein.Module("local") {

    bind<Gson>() with provider {
        GsonBuilder()
            .setLenient()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    bind<CsvReader>() with provider {
        CsvReader().apply {
            setFieldSeparator(';')
            setContainsHeader(false)
        }
    }

    bind<Preferences>() with provider {
        Preferences(instance())
    }

    bind<BellRepository>() with singleton {
        BellRepository()
    }

    bind<VacancyRepository>() with singleton {
        VacancyRepository(instance())
    }

    bind<PlaceRepository>() with singleton {
        PlaceRepository(instance())
    }
}