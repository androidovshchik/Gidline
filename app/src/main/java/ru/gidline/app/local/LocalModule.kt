package ru.gidline.app.local

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val localModule = Kodein.Module("local") {

    bind<Gson>() with provider {
        GsonBuilder()
            .setLenient()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    bind<VacancyRepository>() with provider {
        VacancyRepository(instance(), instance())
    }
}