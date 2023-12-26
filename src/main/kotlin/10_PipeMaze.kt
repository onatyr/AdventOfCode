import java.io.File

class Case(line: Int, column: Int, connexionString: String) {

    val location = Pair(line, column)
    val string = connexionString

    val left = Pair(location.first, location.second - 1)
    val right = Pair(location.first, location.second + 1)
    val top = Pair(location.first - 1, location.second)
    val bottom = Pair(location.first + 1, location.second)

    val connexions = listOfConnexions()
    fun listOfConnexions(): List<Pair<Int, Int>> {
        lateinit var result: List<Pair<Int, Int>>
        when (string) {
            "7" -> result = listOf(left, bottom)
            "J" -> result = listOf(top, left)
            "F" -> result = listOf(bottom, right)
            "L" -> result = listOf(top, right)
            "|" -> result = listOf(top, bottom)
            "-" -> result = listOf(left, right)
            "S" -> result = listOf(top, right)
            else -> result = emptyList<Pair<Int, Int>>()
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
        val nextLocation = currentCase.connexions.filter { it != previousLocation }.first()
        previousLocation = currentCase.location
        currentCase = maze[nextLocation.first][nextLocation.second]

        loopSize++
    }
    return loopSize/2

}