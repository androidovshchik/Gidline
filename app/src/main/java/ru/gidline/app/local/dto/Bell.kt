package ru.gidline.app.local.dto

enum class BellType(val caption: String) {
    INVITE("ПРИГЛАШЕНИЕ"),
    REJECT("ОТКАЗ"),
    SUBSCRIBE("ПОДПИСКА")
}

class Bell {

    lateinit var type: BellType

    lateinit var title: String

    lateinit var subtitle: String

    lateinit var date: String

    lateinit var vacancy: String

    lateinit var html: String
}