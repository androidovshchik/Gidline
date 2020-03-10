package ru.gidline.app.local.model

import android.annotation.SuppressLint
import android.graphics.Color
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.gidline.app.screen.search.model.SearchFilter
import timber.log.Timber

@Suppress("unused")
enum class Schedule(val id: Int, val caption: String, val color: String) {
    FULL_TIME(0, "ПОЛНЫЙ РАБОЧИЙ ДЕНЬ", "#e1619f"),
    REPLACEABLE(1, "СМЕННЫЙ ГРАФИК РАБОТЫ", "#c032b4"),
    SHIFT_METHOD(2, "ВАХТОВЫЙ МЕТОД", "#7b3172"),
    WORK(3, "ПОДРАБОТКА", "#b4186e")
}

@Suppress("MemberVisibilityCanBePrivate")
class Vacancy {

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("logo")
    @Expose
    lateinit var logo: String

    @SerializedName("organization")
    @Expose
    lateinit var organization: String

    @SerializedName("vacancy")
    @Expose
    lateinit var vacancy: String

    @SerializedName("date")
    @Expose
    lateinit var date: String

    @SerializedName("payment")
    @Expose
    lateinit var payment: String

    @SerializedName("time")
    @Expose
    lateinit var perTime: String

    @SerializedName("city")
    @Expose
    lateinit var city: String

    @SerializedName("place")
    @Expose
    lateinit var place: String

    @SerializedName("form")
    @Expose
    lateinit var form: String

    @SerializedName("offer")
    @Expose
    lateinit var offer: String

    @SerializedName("responsibility")
    @Expose
    lateinit var responsibility: String

    @SerializedName("requirement")
    @Expose
    lateinit var requirement: String

    @SerializedName("region")
    @Expose
    lateinit var region: String

    @SerializedName("location")
    @Expose
    lateinit var location: String

    @SerializedName("quickly")
    @Expose
    var quickly = false

    val color: Int
        get() = try {
            Color.parseColor(Schedule.values().first { form.equals(it.caption, true) }.color)
        } catch (e: Throwable) {
            Timber.e(e)
            Color.RED
        }

    val minPayment: Int
        @SuppressLint("DefaultLocale")
        get() = try {
            payment.toUpperCase().substringBefore("РУБ").replace("[^0-9]".toRegex(), "").toInt()
        } catch (e: Throwable) {
            Timber.e(e)
            -1
        }

    val hasPatent: Boolean
        get() = requirement.contains("Есть разрешительные документы на работу", true)

    val hasResidence: Boolean
        get() = offer.contains("Предоставляется общежитие", true)

    val hasFreeFeed: Boolean
        get() = offer.contains("Бесплатное питание", true)

    fun match(searchFilter: SearchFilter): Boolean {
        searchFilter.text?.let {
            if (!vacancy.contains(it, true)) {
                return false
            }
        }
        searchFilter.calculator.perTime?.let { calcPerTime ->
            when (perTime) {
                "В МЕСЯЦ" -> if (calcPerTime != 0) return false
                "ЗА СМЕНУ" -> if (calcPerTime != 1) return false
                "ЗА ЧАС" -> if (calcPerTime != 2) return false
                else -> return false
            }
            if (searchFilter.calculator.progress > 0) {
                if (minPayment < searchFilter.calculator.payment) {
                    return false
                }
            }
        }
        searchFilter.city?.let {
            if (!city.equals(it, true)) {
                return false
            }
        }
        searchFilter.form?.let { searchForm ->
            val schedule = Schedule.values().firstOrNull { it.id == searchForm }
            if (schedule == null || !form.equals(schedule.caption, true)) {
                return false
            }
        }
        if (searchFilter.residence) {
            if (!hasResidence) {
                return false
            }
        }
        if (searchFilter.freeFeed) {
            if (!hasFreeFeed) {
                return false
            }
        }
        return true
    }
}