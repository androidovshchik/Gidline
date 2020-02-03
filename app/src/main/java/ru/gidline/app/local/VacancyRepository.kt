package ru.gidline.app.local

import android.content.Context
import com.google.gson.Gson
import ru.gidline.app.extension.fromJson
import ru.gidline.app.local.dto.Vacancy
import java.io.BufferedReader

class VacancyRepository(context: Context, gson: Gson) {

    private val vacancies = gson.fromJson<List<Vacancy>>(
        context.assets.open("vacancies.json")
            .bufferedReader()
            .use(BufferedReader::readText)
    )

    fun getAll(): List<Vacancy> = vacancies

    fun getById(id: Int) = vacancies.first { it.id == id }
}