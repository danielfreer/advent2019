package intcode

fun execute(memory: Memory): Memory {
    val program = Program(memory)
    return execute(program).memory
}

fun execute(memory: Memory, vararg parameters: Pair<Int, Int>): Int {
    fun Memory.writeImmediate(addressToValue: Map<Int, Int>) = mapIndexed { address, originalValue ->
        addressToValue[address] ?: originalValue
    }

    val modifiedMemory = memory.writeImmediate(parameters.toMap())
    val program = Program(modifiedMemory)
    val result = execute(program)
    return result.memory.readImmediate(0)
}

fun execute(memory: Memory, range: IntRange, condition: (Int) -> Boolean, solution: (Int, Int) -> Int): Int {
    range.forEach { noun ->
        range.forEach { verb ->
            val result = execute(memory, 1 to noun, 2 to verb)
            if (condition(result)) return solution(noun, verb)
        }
    }
    throw IllegalStateException("Could not find noun/verb pair that satisfies condition in: $range")
}

fun execute(memory: Memory, input: Int): Int? {
    val program = Program(memory, instructionPointer = 0, input = input)
    val result = execute(program)
    return result.output
}

private data class Program(
    val memory: Memory,
    val instructionPointer: Int = 0,
    val input: Int? = null,
    val output: Int? = null
) {
    private val operation by lazy { memory.readImmediate(instructionPointer) }
    private val digits by lazy { operation.toString().map(Char::toString).map(String::toInt) }
    val code by lazy { digits.takeLast(2).joinToString(separator = "").toInt() }
    val parameterModeDigits by lazy { digits.dropLast(2).reversed() }
}

private tailrec fun execute(program: Program): Program {
    return when (program.code.let(Opcode::of)) {
        Opcode.ADD -> execute(program.add())
        Opcode.MULTIPLY -> execute(program.multiply())
        Opcode.WRITE -> execute(program.write())
        Opcode.READ -> execute(program.read())
        Opcode.JUMP_IF_TRUE -> execute(program.jumpIf { it != 0 })
        Opcode.JUMP_IF_FALSE -> execute(program.jumpIf { it == 0 })
        Opcode.LESS_THAN -> execute(program.equality { first, second -> first < second })
        Opcode.EQUALS -> execute(program.equality { first, second -> first == second })
        Opcode.HALT -> program
    }
}

private fun Program.add(): Program {
    val (firstParameterMode, secondParameterMode, thirdParameterMode) = parameterModes()
    val firstInput = memory.read(firstParameterMode, instructionPointer + 1)
    val secondInput = memory.read(secondParameterMode, instructionPointer + 2)
    val result = firstInput + secondInput
    return copy(
        memory = memory.write(thirdParameterMode, instructionPointer + 3, result),
        instructionPointer = instructionPointer + 4
    )
}

private fun Program.multiply(): Program {
    val (firstParameterMode, secondParameterMode, thirdParameterMode) = parameterModes()
    val firstInput = memory.read(firstParameterMode, instructionPointer + 1)
    val secondInput = memory.read(secondParameterMode, instructionPointer + 2)
    val result = firstInput * secondInput
    return copy(
        memory = memory.write(thirdParameterMode, instructionPointer + 3, result),
        instructionPointer = instructionPointer + 4
    )
}

private fun Program.write(): Program {
    val firstParameterMode = parameterModes().first
    if (input == null) throw NullPointerException("Input was null")
    return copy(
        memory = memory.write(firstParameterMode, instructionPointer + 1, input),
        instructionPointer = instructionPointer + 2
    )
}

private fun Program.read(): Program {
    val firstParameterMode = parameterModes().first
    val output = memory.read(firstParameterMode, instructionPointer + 1)
    return copy(
        instructionPointer = instructionPointer + 2,
        output = output
    )
}

private fun Program.jumpIf(condition: (Int) -> Boolean): Program {
    val (firstParameterMode, secondParameterMode, _) = parameterModes()
    val firstInput = memory.read(firstParameterMode, instructionPointer + 1)
    val secondInput = memory.read(secondParameterMode, instructionPointer + 2)
    return if (condition(firstInput)) {
        copy(instructionPointer = secondInput)
    } else {
        copy(instructionPointer = instructionPointer + 3)
    }
}

private fun Program.equality(condition: (Int, Int) -> Boolean): Program {
    val (firstParameterMode, secondParameterMode, thirdParameterMode) = parameterModes()
    val firstInput = memory.read(firstParameterMode, instructionPointer + 1)
    val secondInput = memory.read(secondParameterMode, instructionPointer + 2)
    val result = if (condition(firstInput, secondInput)) 1 else 0
    return copy(
        memory = memory.write(thirdParameterMode, instructionPointer + 3, result),
        instructionPointer = instructionPointer + 4
    )
}

private fun Program.parameterModes(): Triple<ParameterMode, ParameterMode, ParameterMode> {
    fun <T> List<T>.getOrDefault(index: Int, defaultValue: T): T {
        return if (index in 0..lastIndex) get(index) else defaultValue
    }

    val parameterModes = parameterModeDigits.map(ParameterMode::of)
    val firstParameterMode = parameterModes.getOrDefault(0, ParameterMode.POSITION)
    val secondParameterMode = parameterModes.getOrDefault(1, ParameterMode.POSITION)
    val thirdParameterMode = parameterModes.getOrDefault(2, ParameterMode.POSITION)
    return Triple(firstParameterMode, secondParameterMode, thirdParameterMode)
}

private fun Memory.read(mode: ParameterMode, address: Int) = when (mode) {
    ParameterMode.POSITION -> readPosition(address)
    ParameterMode.IMMEDIATE -> readImmediate(address)
}

private fun Memory.write(mode: ParameterMode, address: Int, value: Int) = when (mode) {
    ParameterMode.POSITION -> writePosition(address, value)
    ParameterMode.IMMEDIATE -> writeImmediate(address, value)
}
