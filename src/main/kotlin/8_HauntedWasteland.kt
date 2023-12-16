import java.io.File
import kotlin.math.floor

class Node(line: String) {
    val nodeName = line.split(" = ").first()
    val left = line.split(" = ").last().split(", ").first().removePrefix("(")
    val right = line.split(" = ").last().split(", ").last().removeSuffix(")")
}

fun hauntedWasteland(): Long {
    var path = ""
    val allNodes = emptyList<Node>().toMutableList()
    File("src/main/kotlin/hauntedInput").forEachLine {
        if (!it.contains('=')) {
            path += it
        } else {
            allNodes.add(Node(it))
        }
    }

    val currentNodes = allNodes.filter { it.nodeName.last() == 'A' }.toMutableList()
    val steps = emptyList<Int>().toMutableList()
    for (node in currentNodes) {
        var thisNode = node
        var count = 0
        while (thisNode.nodeName.last() != 'Z') {

            when (startAgainIndices(path, count)) {
                'L' -> thisNode = allNodes.find { it.nodeName == thisNode.left }!!
                'R' -> thisNode = allNodes.find { it.nodeName == thisNode.right }!!
            }
            count += 1
        }
        steps.add(count)
    }

    val allFactorsList = emptyList<Map<Int, List<Int>>>().toMutableList()
    steps.forEach { allFactorsList.add(primeFactors(it)) }

    return lcd(allFactorsList)
}

fun startAgainIndices(string: String, index: Int): Char {
    val toReduce = (string.length * floor((index / string.length).toDouble())).toInt()
    return string[index - toReduce]
}

fun primeFactors(n: Int): Map<Int, List<Int>> {
    val factorsList = emptyList<Int>().toMutableList()
    var previousResult = n
    var primeFactor = 2
    while ( !isPrime(previousResult, 2) ) {
        if ( isPrime(primeFactor, 2) && previousResult % primeFactor == 0 ) {
            previousResult /= primeFactor
            factorsList.add(primeFactor)
        }
        else{
            primeFactor++
        }
    }
    factorsList.add(previousResult)
    return factorsList.groupBy { it }
}
fun isPrime(n: Int, i: Int): Boolean {
    if (n == 0 || n == 1) {
        return false
    }
    if (n == i)
        return true

    if (n % i == 0) {
        return false
    }

    return isPrime(n,i+1)
}

fun lcd(allFactorsList: MutableList<Map<Int, List<Int>>>): Long {

    val resultMap = allFactorsList
        .flatMap { it.entries }
        .groupBy({ it.key }, { it.value })
        .mapValues { entry -> entry.value.maxBy { it.size } }

    return resultMap.map { it.key * it.value.size }.fold(1L) { acc, element -> acc * element }
}

