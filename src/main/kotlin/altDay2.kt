import kotlin.time.ExperimentalTime

@ExperimentalTime
fun altDay2(input: List<String>): List<Solution> {
    val memory = input.flatMap { it.split(",") }.map(String::toInt)

    return listOf(
        solve(2, 1) { intcode.execute(memory, 1 to 12, 2 to 2) },
        solve(2, 2) {
            intcode.execute(memory, range = 0..99, condition = { it == 19_690_720 }) { noun, verb ->
                100 * noun + verb
            }
        }
    )
}
