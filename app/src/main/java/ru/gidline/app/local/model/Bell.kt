package ru.gidline.app.local.model

class Bell {

    var id = 0

    lateinit var type: String

    lateinit var title: String

    lateinit var subtitle: String

    lateinit var date: String

    var vacancy: String? = null

    lateinit var html: String

    var unread = false

    companion object {

        const val INVITATION = "ПРИГЛАШЕНИЕ"

        const val REJECTION = "ОТКАЗ"

        const val SUBSCRIPTION = "ПОДПИСКА"
    }
}