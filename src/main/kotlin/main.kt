import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main() {
    listOf(
        day1(loadResource("day1.txt")),
        day2(loadResource("day2.txt")),
        altDay2(loadResource("day2.txt")),
        day3(loadResource("day3.txt")),
        day4(loadResource("day4.txt")),
        day5(loadResource("day5.txt"))
    )
        .flatten()
        .groupBy { it.day }
        .forEach { (day, solution) ->
            println("Day $day")
            solution.forEach { (_, part, solution, duration) ->
                println("\tPart $part Solution: $solution ($duration)")
            }
        }
}
