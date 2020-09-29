import kotlin.time.ExperimentalTime

@ExperimentalTime
fun day5(input: List<String>): List<Solution> {
    val memory = input.flatMap { it.split(",").map(String::toInt) }

    return listOf(
        solve(5, 1) { intcode.execute(memory, input = 1)!! }
    )
}
