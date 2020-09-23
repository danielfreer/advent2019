import intcode.Program
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun day2(input: List<String>): List<Solution> {
    val program = input.flatMap { it.split(",") }.map(String::toInt)

    return listOf(
        solve(2, 1) { executeWithReplacements(program) },
        solve(2, 2) { calculateNounVerb(program) }
    )
}

@ExperimentalTime
fun altDay2(input: List<String>): List<Solution> {
    val memory = input.flatMap { it.split(",") }.map(String::toInt)

    return listOf(
        solve(2, 1) { intcode.execute(Program(memory, 0, null, null), 1 to 12, 2 to 2) },
        solve(2, 2) {
            (0..99).forEach { noun ->
                (0..99).forEach { verb ->
                    val result = intcode.execute(Program(memory, 0, null, null), 1 to noun, 2 to verb)
                    if (result == 19_690_720) return@solve 100 * noun + verb
                }
            }
            throw java.lang.IllegalStateException("Could not find noun/verb pair")
        }
    )
}

private const val ADD = 1
private const val MULTIPLY = 2
private const val RETURN = 99

private tailrec fun execute(state: MutableList<Int>, index: Int): List<Int> {
    return when (val opCode = state[index]) {
        ADD -> {
            state[state[index + 3]] = state[state[index + 1]] + state[state[index + 2]]
            execute(state, index + 4)
        }
        MULTIPLY -> {
            state[state[index + 3]] = state[state[index + 1]] * state[state[index + 2]]
            execute(state, index + 4)
        }
        RETURN -> state
        else -> throw IllegalStateException("Unknown opcode: $opCode")
    }
}

fun execute(program: List<Int>) = execute(program.toMutableList(), 0)

private fun executeWithReplacements(program: List<Int>): Int {
    val mutableProgram = program.toMutableList().apply {
        this[1] = 12
        this[2] = 2
    }
    return execute(mutableProgram, 0)[0]
}

private fun calculateNounVerb(program: List<Int>): Int {
    (0..99).forEach { noun ->
        (0..99).forEach { verb ->
            val mutableProgram = program.toMutableList().apply {
                this[1] = noun
                this[2] = verb
            }
            if (execute(mutableProgram, 0)[0] == 19_690_720) {
                return 100 * noun + verb
            }
        }
    }
    throw java.lang.IllegalStateException("Could not find noun/verb pair")
}
