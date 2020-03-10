package ru.gidline.app.local

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val localModule = Kodein.Module("local") {

    bind<Gson>() with provider {
        GsonBuilder()
            .setLenient()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    bind<Preferences>() with provider {
        Preferences(instance())
    }

    bind<VacancyRepository>() with singleton {
        VacancyRepository(instance(), instance())
    }

    bind<BellRepository>() with singleton {
        BellRepository()
    }
}