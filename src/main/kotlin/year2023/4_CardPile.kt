package year2023

import java.io.File
import kotlin.math.pow

class Card(line: String) {

    private val numbers = line.split(": ").last()
    private val winningNumbers = numbers.split(" | ").first().split(" ").filter { it.isNotEmpty() }
    private val yourNumbers = numbers.split(" | ").last().split(" ").filter { it.isNotEmpty() }

    val matches = winningNumbers.intersect(yourNumbers.toSet()).size
    val powerPoints = if (2.0.pow(matches - 1) >= 1) 2.0.pow(matches - 1).toInt() else 0
}

fun setInstances(index: Int, allLines: MutableList<Pair<Int, String>>, points: Int, instances: Int) {
    for (i in index + 1..index + points) {
        allLines[i] = allLines[i].copy(first = allLines[i].first + instances)
    }
}


fun cardPile(): Int {
    var acc = 0
    val allLines = mutableListOf<Pair<Int, String>>()
    File("src/main/kotlin/cardInput").forEachLine {
        allLines.add(Pair(1, it))
    }
    for (index in allLines.indices) {
        val points = Card(allLines[index].second).matches
        val instances = allLines[index].first
        if (points > 0) {
            setInstances(index, allLines, points, instances)
        }
    }

    for (e in allLines) {
        acc += e.first
//        acc += Card(e.second).powerPoints
    }

    return acc
}