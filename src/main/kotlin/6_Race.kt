import java.io.File

fun tryRaces(race: Pair<Long, Long>): Int{
    var recordTime = 0
    for(holdTime in 1..race.first){
        val distance = (race.first-holdTime)*holdTime
        if (distance>race.second){
            recordTime += 1
        }
    }
    return recordTime
}

fun fastBoat(): Int {
    var acc = 1
//    val raceList = listOf(Pair(53, 250),Pair(91,1330), Pair(67,1081), Pair(68,1025))
//    for (pair in raceList){
//        acc *= tryRaces(pair)
//    }
    val kerningRace = Pair(53916768.toLong(),250133010811025)
    acc = tryRaces(kerningRace)

    return acc
}

