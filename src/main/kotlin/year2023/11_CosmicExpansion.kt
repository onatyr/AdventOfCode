package year2023

import java.io.File

fun parseGalaxies(sky: MutableList<MutableList<Char>>): MutableList<Pair<Int, Int>> {
    val list = emptyList<Pair<Int, Int>>().toMutableList()
    for (i in sky.indices) {
        for (j in sky[i].indices) {
            if (sky[i][j] == '#') {
                list.add(Pair(i, j))
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

fun cosmicExpansion(): Long {
    var sky = emptyList<MutableList<Char>>().toMutableList()
    var lineExpandedIndex = emptyList<Int>().toMutableList()
    var columnExpandedIndex = emptyList<Int>().toMutableList()

    var lineIndex = 0
    File("src/main/kotlin/cosmicInput").forEachLine {
        sky.add(it.toMutableList())
        if (!it.contains("#".toRegex())) {
            lineExpandedIndex.add(lineIndex)
//            sky.add(it.toMutableList())
        }
        lineIndex++
    }

//    sky = addColumns(sky)
    for (i in sky.first().indices) {
        if (checkEmptyColumn(sky, i)) {
            columnExpandedIndex.add(i)
        }
    }

    val galaxiesList = parseGalaxies(sky)

    var pathSum: Long = 0
    for (i in 0..<galaxiesList.size) {
        val firstGalaxy = galaxiesList[i]

        for (j in i + 1..<galaxiesList.size) {
            val secondGalaxy = galaxiesList[j]

            val lineRange = minOf(secondGalaxy.first, firstGalaxy.first)..maxOf(secondGalaxy.first, firstGalaxy.first)
            val columnRange =
                minOf(secondGalaxy.second, firstGalaxy.second)..maxOf(secondGalaxy.second, firstGalaxy.second)

            val multiple = lineExpandedIndex.count { it in lineRange } + columnExpandedIndex.count { it in columnRange }

            pathSum += kotlin.math.abs(lineRange.first - lineRange.last) + kotlin.math.abs(columnRange.first - columnRange.last) + (multiple * 1000000) - multiple
        }

    }

    return pathSum
}
