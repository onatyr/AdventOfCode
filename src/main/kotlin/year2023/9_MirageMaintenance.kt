package year2023

import java.io.File

fun addNewValuesStart(allSubValues: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
    val newSubValuesList = allSubValues.reversed().toMutableList()

    for (i in 1..<newSubValuesList.size) {
        newSubValuesList[i] = (mutableListOf(newSubValuesList[i].first() - newSubValuesList[i - 1].first()) + newSubValuesList[i]).toMutableList()
    }

    return newSubValuesList
}

fun addNewValuesEnd(allSubValues: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
    val newSubValuesList = allSubValues.reversed().toMutableList()

    for (i in 1..<newSubValuesList.size) {
        newSubValuesList[i].add(newSubValuesList[i].last() + newSubValuesList[i - 1].last())
    }

    return newSubValuesList
}

fun isFinal(list: MutableList<Int>): Boolean {
    return list.groupBy { it }.size == 1
}

fun deltasList(originList: MutableList<Int>): MutableList<Int> {
    val resultList = emptyList<Int>().toMutableList()
    for (i in 0..<originList.size - 1) {
        resultList.add(originList[i + 1] - originList[i])
    }
    return resultList
}

fun parseLine(line: String): MutableList<Int> {
    return line.split(" ").map { it.toInt() }.toMutableList()
}

fun findNext(line: String): Long {
    val allSubValues: MutableList<MutableList<Int>> = emptyList<MutableList<Int>>().toMutableList()
    allSubValues.add(parseLine(line))
    allSubValues.add(deltasList(parseLine(line)))

    while (!isFinal(allSubValues.last())) {
        allSubValues.add(deltasList(allSubValues.last()))
    }

    return addNewValuesStart(allSubValues).last().first().toLong()
}

fun mirageMaintenance(): Long {
    val allNextValue = emptyList<Long>().toMutableList()
    File("src/main/kotlin/mirageInput").forEachLine {

        allNextValue.add(findNext(it))

    }

    return allNextValue.reduce { acc, value -> acc + value }
}