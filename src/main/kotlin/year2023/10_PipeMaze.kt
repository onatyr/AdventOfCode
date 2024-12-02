package year2023

import java.io.File

fun loopArea(mazeLine: MutableList<Case>, case: Case): Boolean {

    var count = 0
    var betweenExtremities = false
    var previousExtremityDirection = ""
    for (i in case.location.second + 1..<mazeLine.size) {

        if (mazeLine[i].extremity) {
            val currentExtremityDirection = when (mazeLine[i].string) {
                "7" -> "bottom"
                "F" -> "bottom"
                "L" -> "top"
                "J" -> "top"
                else -> ""
            }

            if (betweenExtremities && previousExtremityDirection != currentExtremityDirection) {
                count++
            }

            betweenExtremities = !betweenExtremities
            previousExtremityDirection = currentExtremityDirection

        }
        if (mazeLine[i].inTheLoop && !betweenExtremities && !mazeLine[i].extremity) {
            count++
        }
    }
    return count % 2 == 1
}

class Case(line: Int, column: Int, connexionString: String) {

    val location = Pair(line, column)
    val string = connexionString
    var inTheLoop = false
    var extremity = false

    fun looped() {
        inTheLoop = true
        if (string != "|" && string != "-") {
            extremity = true
        }
    }

    val left = Pair(location.first, location.second - 1)
    val right = Pair(location.first, location.second + 1)
    val top = Pair(location.first - 1, location.second)
    val bottom = Pair(location.first + 1, location.second)

    val connexions = listOfConnexions()
    val connexionString = emptyList<String>().toMutableList()
    private fun listOfConnexions(): List<Pair<Int, Int>> {
        val result = when (string) {
            "7" -> listOf(left, bottom)
            "J" -> listOf(top, left)
            "F" -> listOf(bottom, right)
            "L" -> listOf(top, right)
            "|" -> listOf(top, bottom)
            "-" -> listOf(left, right)
            "S" -> listOf(top, right)
            else -> emptyList()
        }
        return result
    }
}

fun pipeMaze(): Int {
    val maze = emptyList<MutableList<Case>>().toMutableList()
    var lineIndex = 0
    var startLocation = Pair(0, 0)
    File("src/main/kotlin/mazeInput").forEachLine {
        val lineCasesString = it.split("")
        val lineCases = emptyList<Case>().toMutableList()

        for (columnIndex in lineCasesString.indices) {
            if (lineCasesString[columnIndex] == "S") {
                startLocation = Pair(lineIndex, columnIndex)
            }
            lineCases.add(Case(lineIndex, columnIndex, lineCasesString[columnIndex]))
        }

        maze.add(lineCases)
        lineIndex++
    }
    var currentCase = maze[startLocation.first][startLocation.second]
    var previousLocation = currentCase.right
    var loopSize = 0

    while (!(currentCase.string == "S" && loopSize > 0)) {
        maze[currentCase.location.first][currentCase.location.second].looped()
        val nextLocation = currentCase.connexions.first { it != previousLocation }
        previousLocation = currentCase.location
        currentCase = maze[nextLocation.first][nextLocation.second]

        loopSize++
    }

    var inArea = 0

    maze.forEach {
        for (case in it) {
            if (!case.inTheLoop && loopArea(it, case)) {
                inArea++
            }
        }
    }


    return inArea

}