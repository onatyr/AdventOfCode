package year2023

import java.io.File

class Hand(handString: String) {
    val handValue = handString.filterNot { it == 'J' }
    val cardList = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')
    val handIntValue = handString.toCharArray().map { cardList.indexOf(it) }
    val jokers = handString.count { it == 'J' }
    val typeValue = type()


    private fun type(): Int {
        if (jokers == 5) return 7
        if (isFive()) return 7
        if (isFour()) return 6
        if (isHouse()) return 5
        if (isThree()) return 4
        if (isTwoPair()) return 3
        if (isOnePair()) return 2
        if (isHigh()) return 1
        return 0
    }


    private fun isFive(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }.toMutableList()
        counts[counts.indexOf(counts.max())] += jokers
        return counts.any { it == 5 }
    }

    private fun isFour(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }.toMutableList()
        counts[counts.indexOf(counts.max())] += jokers
        return counts.any { it == 4 }
    }

    private fun isHouse(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }.toMutableList()
        counts[counts.indexOf(counts.max())] += jokers
        return counts.any { it == 3 } && counts.any { it == 2 }
    }

    private fun isThree(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }.toMutableList()
        counts[counts.indexOf(counts.max())] += jokers
        return counts.any { it == 3 }
    }

    private fun isTwoPair(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }.toMutableList()
        counts[counts.indexOf(counts.max())] += jokers
        return counts.count { it == 2 } == 2
    }

    private fun isOnePair(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }.toMutableList()
        counts[counts.indexOf(counts.max())] += jokers
        return counts.count { it == 2 } == 1
    }

    private fun isHigh(): Boolean {
        return !handValue.any { e -> handValue.count { it == e } != 1 } && jokers == 0
    }
}

fun camelCards(): Int {
    var acc = 0
    val allHands = mutableListOf<Pair<Hand, Int>>()
    File("src/main/kotlin/camelCardInput").forEachLine {
        allHands.add(Pair(Hand(it.split(" ").first()), it.split(" ").last().toInt()))
    }

    val sortedHands = allHands.sortedWith(
        compareBy({ it.first.typeValue },
            { it.first.handIntValue[0] },
            { it.first.handIntValue[1] },
            { it.first.handIntValue[2] },
            { it.first.handIntValue[3] },
            { it.first.handIntValue[4] })
    )

    for (i in sortedHands.indices) {
        acc += sortedHands[i].second * (i + 1)
    }


    return acc
}