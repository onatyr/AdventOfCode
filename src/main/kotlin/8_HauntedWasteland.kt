import java.io.File
import kotlin.math.floor

class Node(line: String) {
    val nodeName = line.split(" = ").first()
    val left = line.split(" = ").last().split(", ").first().removePrefix("(")
    val right = line.split(" = ").last().split(", ").last().removeSuffix(")")
}

fun hauntedWasteland(): Int {
    var path = ""
    val allNodes = emptyList<Node>().toMutableList()
    File("src/main/kotlin/hauntedInput").forEachLine {
        if (!it.contains('=')) {
            path += it
        }
        allNodes.add(Node(it))
    }

    println(path)

    var thisNode = allNodes.find { it.nodeName == "AAA" }!!
    var count = 0
    while (thisNode.nodeName != "ZZZ"){
        print("${thisNode.nodeName} to ")

        when(startAgainIndices(path, count)){
            'L' -> thisNode = allNodes.find { it.nodeName == thisNode.left }!!
            'R' -> thisNode = allNodes.find { it.nodeName == thisNode.right }!!
        }
        println(thisNode.nodeName)
        count += 1
    }
    return count
}

fun startAgainIndices(string: String, index: Int): Char{
    val toReduce = ( string.length * floor( (index/string.length).toDouble() ) ).toInt()
    return string[index-toReduce]
}

