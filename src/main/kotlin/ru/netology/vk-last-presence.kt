package ru.netology

import java.util.*

fun main() {
    while (true) {
        println("Время последнего пребывания (с):")
        val scanner = Scanner(System.`in`)
        val timeLastPresence = scanner.nextInt()
        agoToText(timeLastPresence)
    }
}

fun agoToText(timeLastPresence: Int) =
        when (timeLastPresence) {
            in 0..60 -> println("Был(а) только что")
            in 61..60 * 60 -> println("Был(а)" +
                    " ${timeLastPresence / 60} ${declensionOfMinutes(timeLastPresence / 60)} назад")
            in (60 * 60 + 1)..(24 * 60 * 60) -> println("Был(а)" +
                    " ${timeLastPresence / (60 * 60)} " +
                    "${declensionOfHours(timeLastPresence / (60 * 60))} назад")
            in (24 * 60 * 60 + 1)..(24 * 60 * 60) * 2 -> println("Был(а) сегодня")
            in (24 * 60 * 60) * 2 + 1..(24 * 60 * 60) * 3 -> println("Был(а) вчера")
            else -> println("Был(а) давно")
        }

fun declensionOfMinutes(timeLastPresence: Int): String {
    val timeToString = timeLastPresence.toString()
    return when {
        timeToString.matches(Regex("[2-5]*1$")) -> "минуту"
        timeToString.matches(Regex("[2-5]*[2-4]$")) -> "минуты"
        else -> "минут"
    }
}

fun declensionOfHours(timeLastPresence: Int): String {
    val timeToString = timeLastPresence.toString()
    return when {
        timeToString.matches(Regex("(^1$|^21$)")) -> "час"
        timeToString.matches(Regex("(^[2-4]$|^[22-23]*$)")) -> "часа"
        else -> "часов"
    }
}