import java.io.File

fun checkPossible(line: String): Int {
    val possibilities = mapOf<String, Int>("red" to 12, "green" to 13, "blue" to 14)
    val gameId = line.split("Game ")[1].split(": ")[0].toInt()
    val inCube = line.split("Game ")[1].split(": ")[1].split(";")
    println(line)
    println(inCube)
    inCube.map { set ->
        for (s in set.split(", ")) {
            val newS = s.split(" ").filter { it.length > 0 }
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
    var accu = 0
    File("src/main/kotlin/cubeInput").forEachLine {
        accu += checkPossible(it)
    }
    return accu
}
