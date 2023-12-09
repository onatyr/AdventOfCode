import java.io.File


fun sumNumbersInLine(line: String, allLines: MutableList<String>): Int {
    var sum = 0
    val surroundedLine = "..$line.."

    val numberPattern = Regex("\\b\\d+\\b")

    val matches = numberPattern.findAll(surroundedLine)

    for (match in matches) {

        val matchValue = match.value.toInt()
        val matchRange = match.range

        val previousLine =
            if (allLines.indexOf(line) > 0) "..${allLines[allLines.indexOf(line) - 1]}.." else ".".repeat(surroundedLine.length)
        val nextLine =
            if (allLines.indexOf(line) < allLines.size - 1) "..${allLines[allLines.indexOf(line) + 1]}.." else ".".repeat(
                surroundedLine.length
            )

        val adjacentSubstring = previousLine.substring(matchRange.first - 1, matchRange.last + 2) + nextLine.substring(
            matchRange.first - 1,
            matchRange.last + 2
        ) + surroundedLine[matchRange.first - 1] + surroundedLine[matchRange.last + 1]

        if (isThereASymbol(adjacentSubstring)) {
            sum += matchValue
        }
    }
    return sum
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
    for (line in allLines) {
        acc += sumNumbersInLine(line, allLines)
    }

    return acc
}