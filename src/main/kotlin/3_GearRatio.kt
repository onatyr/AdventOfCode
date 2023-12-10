import java.io.File


fun sumNumbersInLine(lineIndex: Int, allLines: MutableList<String>): Int {
    var sum = 0
    val surroundedLine = "..${allLines[lineIndex]}.."

    val numberPattern = Regex("\\b\\d+\\b")
    val gearPattern = Regex("\\*")

    val matches = numberPattern.findAll(surroundedLine)

    for (match in matches) {

        val matchValue = match.value.toInt()
        val matchRange = match.range

        val previousLine =
            surroundingLines(lineIndex, allLines, "previous")
        val nextLine =
            surroundingLines(lineIndex, allLines, "next")

        val previousSubstring = previousLine.substring(matchRange.first - 1, matchRange.last + 2)
        val nextSubstring = nextLine.substring(matchRange.first - 1, matchRange.last + 2)
        val surroundingSubstring = "${surroundedLine[matchRange.first - 1]}${surroundedLine[matchRange.last + 1]}"

        val adjacentSubstring = previousSubstring + nextSubstring + surroundingSubstring

        if (isThereASymbol(adjacentSubstring)) {
            sum += matchValue
        }

    }
    return sum
}

fun gearMultiplication(lineIndex: Int, allLines: MutableList<String>): Int {
    var sum = 0
    val surroundedLine = "..${allLines[lineIndex]}.."
    val previousLine = surroundingLines(lineIndex, allLines, "previous")
    val nextLine = surroundingLines(lineIndex, allLines, "next")


    val numberPattern = Regex("\\b\\d+\\b")
    val gearPattern = Regex("\\*")
    val threeLineNumbers = numberPattern.findAll(surroundedLine).toMutableList()
    threeLineNumbers.addAll(numberPattern.findAll(previousLine).toMutableList())
    threeLineNumbers.addAll(numberPattern.findAll(nextLine).toMutableList())

    val gearMatches = gearPattern.findAll(surroundedLine)

    for (gear in gearMatches) {

        val gearRange: IntRange = gear.range.first - 1..gear.range.last + 1

        val surroundingNumbers =
            threeLineNumbers.filter { gearRange.intersect(it.range).isNotEmpty() }.toMutableList()

        sum += if (surroundingNumbers.size == 2) (surroundingNumbers[0].value.toInt() * surroundingNumbers[1].value.toInt()) else 0
    }
    return sum
}

fun surroundingLines(index: Int, allLines: MutableList<String>, where: String): String {
    var result: String = ""
    when (where) {
        "previous" -> result = if (index > 0) "..${allLines[index - 1]}.." else ".".repeat(allLines[index].length + 2)
        "next" -> result = if (index < allLines.size - 1) "..${allLines[index + 1]}.." else ".".repeat(
            allLines[index].length + 2
        )
    }
    return result
}

fun isThereASymbol(adjacentSubstring: String): Boolean {
    val symbols = listOf(
        "=",
        "(",
        ")",
        "+",
        "-",
        "*",
        "/",
        "#",
        "$",
        ":",
        ";",
        "<",
        ">",
        "[",
        "]",
        "{",
        "}",
        "|",
        "^",
        "~",
        "@",
        "=",
        "&",
        "%"
    )
    for (symbol in adjacentSubstring) {
        if (symbols.contains(symbol.toString())) {
            return true
        }
    }
    return false
}


fun gearRatio(): Int {
    var acc = 0
    val allLines = mutableListOf<String>()
    File("src/main/kotlin/gearInput").forEachLine {
        allLines.add(it)
    }
    for (lineIndex in allLines.indices) {
        acc += gearMultiplication(lineIndex, allLines)
    }

    return acc
}