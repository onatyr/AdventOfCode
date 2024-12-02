package year2023

import java.io.File

fun lettersToDigit(line: String): String {
    val digitToLetters =
        arrayListOf<String>(
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9"
        )

    val extractedDigits = mutableMapOf<Int, String>()
    for (i in digitToLetters.indices) {
        val pattern = digitToLetters[i].toRegex()
        val match = pattern.findAll(line)
        for (e in match) {
            val m = e.value
            val indexOfMatch = e.range.first
            extractedDigits[indexOfMatch] = if (i < 10) digitToLetters.indexOf(m).toString() else m
        }
    }
    return extractedDigits.toSortedMap().values.joinToString("")
}

fun getCalibration(line: String): Int {
    val digits = line.filter { it.isDigit() }
    return ("${digits.first()}${digits.last()}").toInt()
}

fun trebuchet(): Int {
    var accu = 0
    File("src/main/kotlin/trebuchetInput").forEachLine {

        val digitsList = lettersToDigit(it)
        accu += getCalibration(digitsList)
    }
    return accu
}
