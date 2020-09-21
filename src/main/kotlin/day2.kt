fun main(vararg args: String) {
    val input = args.toList().ifEmpty { loadResource("day2.txt") }
    val program = input.flatMap { it.split(",") }.map(String::toInt)

    val finalState = executeWithReplacements(program, listOf(1 to 12, 2 to 2))
    println("Day 2 - Part 1 Solution")
    println("Value at position 0: ${finalState[0]}")
    println()
    val (noun, verb) = calculateNounVerb { noun, verb ->
        executeWithReplacements(program, listOf(1 to noun, 2 to verb))[0] == 19_690_720
    }
    println("Day 2 - Part 2 Solution")
    val result = 100 * noun + verb
    println("100 * $noun + $verb = $result")
}

private const val ADD = 1
private const val MULTIPLY = 2
private const val RETURN = 99

tailrec fun execute(state: MutableList<Int>, index: Int): List<Int> {
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

fun executeWithReplacements(program: List<Int>, replacements: List<Pair<Int, Int>>): List<Int> {
    val mutableProgram = program.toMutableList()
    replacements.forEach { (index, value) ->
        mutableProgram[index] = value
    }
    return execute(mutableProgram, 0)
}

fun calculateNounVerb(condition: (Int, Int) -> Boolean): Pair<Int, Int> {
    (0..99).forEach { noun ->
        (0..99).forEach { verb ->
            if (condition(noun, verb)) return noun to verb
        }
    }
    throw java.lang.IllegalStateException("Could not find noun/verb pair")
}
