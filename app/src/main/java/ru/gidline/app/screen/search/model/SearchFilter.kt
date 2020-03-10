package ru.gidline.app.screen.search.model

@Suppress("MemberVisibilityCanBePrivate")
class SearchFilter {

    var text: String? = null

    var region: String? = null

    var city: String? = null

    var form: Int? = null

    var residence = false

    var freeFeed = false

    val calculator = Calculator()
}