import java.io.File

fun parseGalaxies(sky: MutableList<MutableList<Char>>): MutableList<Pair<Int, Int>> {
    val list = emptyList<Pair<Int, Int>>().toMutableList()
    for (i in sky.indices){
        for(j in sky[i].indices){
            if(sky[i][j] == '#'){
                list.add(Pair(i,j))
            }
        }
    }
    return list
}
fun addColumns(sky: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
    var newSky = sky

    val columnIndices = emptyList<Int>().toMutableList()
    for (i in sky.first().indices) {
        if (checkEmptyColumn(sky, i)) {
            columnIndices.add(i)
        }
    }
    for (i in columnIndices.indices) {
        newSky = newSky.map {
            (it.joinToString("").substring(0, columnIndices[i] + i) + "." + it.joinToString("")
                .substring(columnIndices[i] + i)).toMutableList()
        }.toMutableList()
    }

    return newSky
}
fun checkEmptyColumn(sky: MutableList<MutableList<Char>>, columnIndex: Int): Boolean {
    for (i in sky.indices) {
        if (sky[i][columnIndex] == '#') {
            return false
        }
    }
    return true
}

fun cosmicExpansion(): Int {
    var sky = emptyList<MutableList<Char>>().toMutableList()
    File("src/main/kotlin/cosmicInput").forEachLine {
        sky.add(it.toMutableList())
        if (!it.contains("#".toRegex())) {
            sky.add(it.toMutableList())
        }
    }

    sky = addColumns(sky)
    val galaxiesList = parseGalaxies(sky)

    var pathSum = 0
    for (i in 0..<galaxiesList.size){
        val firstGalaxy = galaxiesList[i]
        for (j in i+1..<galaxiesList.size){
            val secondGalaxy = galaxiesList[j]
            pathSum += kotlin.math.abs(secondGalaxy.first - firstGalaxy.first) + kotlin.math.abs(secondGalaxy.second - firstGalaxy.second)
        }

    }

    return pathSum
}
