import java.io.File
import kotlin.math.pow

fun cardPoints(lineIndex: Int, allLines: MutableList<String>): Int {

    val numbers = allLines[lineIndex].split(": ").last()
    val winningNumbers = numbers.split(" | ").first().split(" ").filter { it.isNotEmpty() }
    val yourNumbers = numbers.split(" | ").last().split(" ").filter { it.isNotEmpty() }

    val sameValues = winningNumbers.intersect(yourNumbers.toSet())

    return if (2.0.pow(sameValues.size - 1) >= 1) 2.0.pow(sameValues.size - 1).toInt() else 0
}

fun cardPile(): Int {
    var acc = 0
    val allLines = mutableListOf<String>()
    File("src/main/kotlin/cardInput").forEachLine {
        allLines.add(it)
    }
    for (lineIndex in allLines.indices) {
        acc += cardPoints(lineIndex, allLines)
    }

    return acc
}