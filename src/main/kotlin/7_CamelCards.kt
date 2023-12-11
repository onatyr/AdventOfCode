import java.io.File

class Hand(handString: String) {
    val handValue = handString
    val cardList = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val handIntValue = handString.toCharArray().map { cardList.indexOf(it) }
    val typeValue = type()


    private fun type(): Int {
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
        return handValue.count { it == handValue.first() } == 5
    }

    private fun isFour(): Boolean {
        if (handValue.count { it == handValue.first() } == 4) return true
        else if (handValue.count { it == handValue.last() } == 4) return true
        return false
    }

    private fun isHouse(): Boolean {
        for (char in handValue) {
            if (handValue.count { it == char } !in 2..3) return false
        }
        return true
    }

    private fun isThree(): Boolean {
        return (handValue.any { e -> handValue.count { it == e } == 3 } && handValue.any { e -> handValue.count { it == e } == 1 })
    }

    private fun isTwoPair(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }
        return (counts.count { it == 2 } == 2)
    }

    private fun isOnePair(): Boolean {
        val counts = handValue.groupBy { it }.values.map { it.size }
        return (counts.count { it == 2 } == 1 && counts.count { it == 1 } == 3)
    }

    private fun isHigh(): Boolean {
        return !handValue.any { e -> handValue.count { it == e } != 1 }
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