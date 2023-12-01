import java.io.File

fun getCalibration(line: String): Int {
    var firstValue = ' '
    var secondValue = ' '
    for (i in line.indices) {
        if (line[i].isDigit()) {
            firstValue = if (firstValue.isWhitespace()) line[i] else firstValue
            secondValue = line[i]
        }
    }
    return ("$firstValue$secondValue").toInt()
}

fun readAndSum(): Int {
    var accu = 0
    File("src/main/kotlin/trebuchetInput").forEachLine {
        accu += getCalibration(it)
    }
    return accu
}
