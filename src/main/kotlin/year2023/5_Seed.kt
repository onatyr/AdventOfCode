package year2023

import java.io.File

data class Seed(val number: Long, var location: Long)
fun seedLocation(seed: Seed, parsers: List<ParsedMap>): Long {
    var seedNumber = seed.number
    for(parser in parsers){
        seedNumber = parser.convertToDestination(seedNumber)
    }
    return seedNumber
}

fun locationToSeed(location: Long, parsers: List<ParsedMap>): Long {
    var sourceNumber = location
    for(parser in parsers.reversed()){
        sourceNumber = parser.convertToSource(sourceNumber)
    }
    return sourceNumber
}
class ParsedMap(private val input: String, private val startDelimiter: String, private val endDelimiter: String) {

    val sourceRanges = parseRanges(1)
    val destinationRanges = parseRanges(0)

    private fun parseRanges(startColumn: Int): List<LongRange> {
        val rangeStrings = input.split("$startDelimiter:\n").last().split(
            "\n\n$endDelimiter"
        ).first().split("\n").map { it.split(" ") }
        return rangeStrings.map { LongRange(it[startColumn].toLong(), it[startColumn].toLong() + it[2].toLong() -1) }
    }

    fun convertToDestination(number: Long): Long{
        for(rangeIndex in sourceRanges.indices){
            if (number in sourceRanges[rangeIndex]){
                return number-(sourceRanges[rangeIndex].first()-destinationRanges[rangeIndex].first())
            }
        }
        return number
    }
    fun convertToSource(number: Long): Long{
        for(rangeIndex in destinationRanges.indices){
            if (number in destinationRanges[rangeIndex]){
                return number-(destinationRanges[rangeIndex].first()-sourceRanges[rangeIndex].first())
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
    val listParser: MutableList<ParsedMap> = emptyList<ParsedMap>().toMutableList()
    for (i in 0..<delimiters.size-1) {
        listParser.add(ParsedMap(allLines, delimiters[i], delimiters[i+1]))
    }
//    val seedsListPt1 = allLines.split("seeds: ").last().split("\n\n").first().split(" ").map { Seed(it.toLong(), -1) }.toMutableList()
//    seedsListPt1.forEach { it.location = seedLocation(it, listParser) }
//    seedsListPt1.sortBy { it.location }
//    return seedsListPt1.first().location

    val seedsString = allLines.split("seeds: ").last().split("\n\n").first().split(" ")
    val seedsRangeListPt2: MutableList<LongRange> = emptyList<LongRange>().toMutableList()
    for (i in 0..<seedsString.size-1 step 2){
        val newRange = LongRange(seedsString[i].toLong(),seedsString[i].toLong() + seedsString[i+1].toLong())
        seedsRangeListPt2.add(newRange)
    }

    var continueLoop = true
    var init = 46.toLong()
    while(continueLoop){
        val seedNumber = locationToSeed(init, listParser)
        for (range in seedsRangeListPt2){
            if(seedNumber in range){
                continueLoop = false
                break
            }
        }
        init += 1
    }
    


return init-1

}