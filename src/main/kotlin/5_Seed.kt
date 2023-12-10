import java.io.File

data class Seed(val number: Long, var location: Long)
fun seedLocation(seed: Seed, parsers: List<ParsedMap>): Long {
    var seedNumber = seed.number
    for(parser in parsers){
        seedNumber = parser.convert(seedNumber)
    }
    return seedNumber
}
class ParsedMap(private val input: String, private val startDelimiter: String, private val endDelimiter: String) {

    private val sourceRanges = parseRanges(1)
    private val destinationRanges = parseRanges(0)

    private fun parseRanges(startColumn: Int): List<LongRange> {
        val rangeStrings = input.split("$startDelimiter:\n").last().split(
            "\n\n$endDelimiter"
        ).first().split("\n").map { it.split(" ") }
        return rangeStrings.map { LongRange(it[startColumn].toLong(), it[startColumn].toLong() + it[2].toLong() -1) }
    }

    fun convert(number: Long): Long{
        for(rangeIndex in sourceRanges.indices){
            if (number in sourceRanges[rangeIndex]){
                return number-(sourceRanges[rangeIndex].first()-destinationRanges[rangeIndex].first())
            }
        }
        return number
    }
}


fun seedLocation(): Long {
    var allLines = ""
    File("src/main/kotlin/seedsInput").forEachLine {
        allLines += "$it\n"
    }
    allLines += "\nend"

    val delimiters = allLines.split("\n\n").map { it.split(":").first() }.filter { it != "seeds" }
    println(delimiters)
    val listParser: MutableList<ParsedMap> = emptyList<ParsedMap>().toMutableList()
    for (i in 0..<delimiters.size-1) {
        listParser.add(ParsedMap(allLines, delimiters[i], delimiters[i+1]))
    }
    val seedsList = allLines.split("seeds: ").last().split("\n\n").first().split(" ").map { Seed(it.toLong(), -1) }.toMutableList()
    seedsList.forEach { it.location = seedLocation(it, listParser) }
    seedsList.sortBy { it.location }

    return seedsList.first().location
}