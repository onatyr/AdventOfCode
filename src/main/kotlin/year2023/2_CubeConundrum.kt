package year2023

import java.io.File

fun powerOfSets(line: String): Int {
    val inCube = line.split("Game ")[1].split(": ")[1].split(";")

    val minValues = mutableMapOf<String, Int>("red" to 1, "green" to 1, "blue" to 1)

    inCube.map { set ->
        for (s in set.split(", ")) {
            val newS = s.split(" ").filter { it.isNotEmpty() }
            val number = newS[0].toInt()
            val color = newS[1]
            if (minValues[color]!! < number){
                minValues[color] = number
            }
        }
    }

    return minValues["red"]!! * minValues["green"]!! *minValues["blue"]!!
}

fun checkPossible(line: String): Int {
    val possibilities = mapOf<String, Int>("red" to 12, "green" to 13, "blue" to 14)
    val gameId = line.split("Game ")[1].split(": ")[0].toInt()
    val inCube = line.split("Game ")[1].split(": ")[1].split(";")

    inCube.map { set ->
        for (s in set.split(", ")) {
            val newS = s.split(" ").filter { it.isNotEmpty() }
            val number = newS[0].toInt()
            val color = newS[1]
            if (possibilities[color]!! < number) {
                return 0
            }
        }
    }

    return gameId
}

fun cubeConundrum(): Int {
    var acc = 0
    File("src/main/kotlin/cubeInput").forEachLine {
//        accu += checkPossible(it)
        acc += powerOfSets(it)
    }
    return acc
}
