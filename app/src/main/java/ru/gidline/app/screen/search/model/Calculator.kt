package ru.gidline.app.screen.search.model

@Suppress("MemberVisibilityCanBePrivate")
class Calculator {

    var perTime: Int? = null

    var progress = 0

    val minPayment: Int
        get() = when (perTime) {
            0 -> 20_000
            1 -> 500
            2 -> 50
            else -> 0
        }

    val payment: Int
        get() = minPayment + payStep * progress

    val maxPayment: Int
        get() = when (perTime) {
            0 -> 100_000
            1 -> 5000
            2 -> 500
            else -> 100
        }

    val payStep: Int
        get() = when (perTime) {
            0 -> 5000
            1 -> 500
            2 -> 50
            else -> 1
        }

    fun setFrom(calculator: Calculator) {
        perTime = calculator.perTime
        progress = calculator.progress
    }
}