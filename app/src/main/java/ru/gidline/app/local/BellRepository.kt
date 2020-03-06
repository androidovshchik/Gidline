package ru.gidline.app.local

import android.content.Context
import com.google.gson.Gson
import ru.gidline.app.local.dto.Bell
import ru.gidline.app.local.dto.BellType
import ru.gidline.app.local.dto.Vacancy

class BellRepository(context: Context, gson: Gson) {

    private val bells = arrayListOf(
        Bell().apply {
            type = BellType.INVITE
            title = "ПРИГЛАШЕНИЕ НА СОБЕСЕДОВАНИЕ"
            subtitle = "В ООО Колокольчик"
            date = "14.08.2020"
            vacancy = "КЛАДОВЩИК"
            html = """
                Здравствуйте,<br>
                Хуршед!<br><br>
                Ваше резюме показалось нам интересным. Приглашаем Вас на собеседование.<br><br>
                Если наше предложение Вам, интересно, перезвоните пожалуйста, в рабочее время по телефону.<br><br>
                С уважением,<br>
                Никитина<br>
                Марфа Петровна. 
            """.trimIndent()
        },
        Bell().apply {
            type = BellType.REJECT
            title = "ОТКАЗ НА ОТКЛИК ПО ВАКАНСИИ"
            subtitle = "В ООО Колокольчик"
            date = "14.08.2020"
            vacancy = "КЛАДОВЩИК"
            html = """
                Здравствуйте,<br>
                Хуршед!<br><br>
                Наша компания благодарна за проявленный Вами интерес. К нашему сожалению, Ваша кандидатура не соответствует предъявляемым к имеющейся вакансии требованиям.<br><br>
                Мы учтем Ваше предложение на будущее. При наличии подходящих вакансий, предложим Вам вакантную должность.<br><br>
                С уважением,<br>
                Никитина<br>
                Марфа Петровна.  
            """.trimIndent()
        },
        Bell().apply {
            type = BellType.SUBSCRIBE
            title = "ПРИГЛАШЕНИЕ НА СОБЕСЕДОВАНИЕ"
            subtitle = "В ООО Колокольчик"
            date = "14.08.2020"
            vacancy = "КЛАДОВЩИК"
            html = """
                Вы подключили подписку автоматической рассылки Вашего резюме по базе вакансий Gidline.<br><br>
                В течении срока действия подписки помощник за Вас отберёт вакансии в соответствии с Вашими анкетными данными и осуществит рассылку потенциальным работодателем.<br><br>
                Ваши отклики на вакансии, Вы сможете увидеть в личном кабинете Gidline «Моё резюме».<br><br>
                <b>Подписка действительна до 24.08.2020г.</b><br><br>
                <b>Спасибо за Ваше доверие!</b><br><br>
                <b>Команда Gidline.</b>
            """.trimIndent()
        }
    )

    fun getAll(): List<Vacancy> = vacancies

    fun getById(id: Int) = vacancies.first { it.id == id }
}