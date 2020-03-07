package ru.gidline.app.local

import ru.gidline.app.local.dto.Bell
import java.util.concurrent.CopyOnWriteArrayList

class BellRepository {

    private val bells = CopyOnWriteArrayList(listOf(
        Bell().apply {
            id = 1
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
            id = 2
            title = "ОТКАЗ НА ОТКЛИК ПО ВАКАНСИИ"
            subtitle = "В ООО Колокольчик"
            date = "12.08.2020"
            vacancy = "КОМПЛЕКТОВЩИК"
            html = """
                Здравствуйте,<br>
                Хуршед!<br><br>
                Наша компания благодарна за проявленный Вами интерес. К нашему сожалению, Ваша кандидатура не соответствует предъявляемым к имеющейся вакансии требованиям.<br><br>
                Мы учтем Ваше предложение на будущее. При наличии подходящих вакансий, предложим Вам вакантную должность.<br><br>
                С уважением,<br>
                Никитина<br>
                Марфа Петровна.  
            """.trimIndent()
            read = true
        },
        Bell().apply {
            id = 3
            title = "ПРИГЛАШЕНИЕ НА СОБЕСЕДОВАНИЕ"
            subtitle = "ДО 24.08.2020"
            date = "10.08.2020"
            vacancy = null
            html = """
                Вы подключили подписку автоматической рассылки Вашего резюме по базе вакансий Gidline.<br><br>
                В течении срока действия подписки помощник за Вас отберёт вакансии в соответствии с Вашими анкетными данными и осуществит рассылку потенциальным работодателем.<br><br>
                Ваши отклики на вакансии, Вы сможете увидеть в личном кабинете Gidline «Моё резюме».<br><br>
                <b>Подписка действительна до 24.08.2020г.</b><br><br>
                <b>Спасибо за Ваше доверие!</b><br><br>
                <b>Команда Gidline.</b>
            """.trimIndent()
            read = true
        }
    ))

    val count: Int
        get() = bells.size

    fun getAll() = bells

    fun getById(id: Int) = bells.first { it.id == id }

    fun deleteById(id: Int) = bells.removeAll { it.id == id }
}