package ru.gidline.app.local.model

enum class BellType(val caption: String) {
    INVITATION("ПРИГЛАШЕНИЕ"),
    REJECTION("ОТКАЗ"),
    SUBSCRIPTION("ПОДПИСКА")
}

class Bell {

    var id = 0

    lateinit var type: BellType

    lateinit var title: String

    lateinit var subtitle: String

    lateinit var date: String

    var vacancy: String? = null

    lateinit var html: String

    var unread = false
}