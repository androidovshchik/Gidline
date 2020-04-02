package ru.gidline.app.local.repository

import android.content.Context
import com.google.gson.Gson
import ru.gidline.app.extension.fromJson
import ru.gidline.app.local.model.Vacancy
import java.io.BufferedReader

class VacancyRepository(private val gson: Gson) : BaseRepository<Vacancy>() {

    private val vacancies = mutableListOf<Vacancy>()

    override fun getAll() = vacancies

    fun getById(id: Int) = vacancies.first { it.id == id }

    override fun initData(context: Context) {
        vacancies.clear()
        vacancies.addAll(
            gson.fromJson<List<Vacancy>>(
                context.assets.open("vacancies.json")
                    .bufferedReader()
                    .use(BufferedReader::readText)
            )
        )
    }
}