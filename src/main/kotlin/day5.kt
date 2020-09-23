import intcode.Program
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun day5(input: List<String>): List<Solution> {
    val memory = input.flatMap { it.split(",").map(String::toInt) }

    return listOf(
        solve(5, 1) {
            val program = Program(memory, 0, 1, null)
            intcode.execute(program).second!!
        }
    )
}
