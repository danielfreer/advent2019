package intcode

private const val ADD = 1
private const val MULTIPLY = 2
private const val INPUT = 3
private const val OUTPUT = 4
private const val HALT = 99

fun execute(program: Program, vararg parameters: Pair<Int, Int>): Int {
    val modifiedProgram = program.copy(memory = program.memory.writeAllImmediate(parameters.toMap()))
    val result = execute(modifiedProgram).first
    return result[0]
}

tailrec fun execute(program: Program): Pair<Memory, Int?> {
    return when (val opcode = program.opcode) {
        ADD -> execute(program.add())
        MULTIPLY -> execute(program.multiply())
        INPUT -> execute(program.input())
        OUTPUT -> execute(program.output())
        HALT -> program.memory to program.output
        else -> throw IllegalStateException("Unknown opcode: $opcode")
    }
}

private fun Program.add(): Program {
    val firstInput = memory.read(parameterModes.getOrNull(0), instructionPointer + 1)
    val secondInput = memory.read(parameterModes.getOrNull(1), instructionPointer + 2)
    val result = firstInput + secondInput
    return copy(
        memory = memory.write(parameterModes.getOrNull(2), instructionPointer + 3, result),
        instructionPointer = instructionPointer + 4
    )
}

private fun Program.multiply(): Program {
    val firstInput = memory.read(parameterModes.getOrNull(0), instructionPointer + 1)
    val secondInput = memory.read(parameterModes.getOrNull(1), instructionPointer + 2)
    val result = firstInput * secondInput
    return copy(
        memory = memory.write(parameterModes.getOrNull(2), instructionPointer + 3, result),
        instructionPointer = instructionPointer + 4
    )
}

private fun Program.input(): Program {
    if (input == null) throw NullPointerException("Input was null")
    return copy(
        memory = memory.write(parameterModes.getOrNull(0), instructionPointer + 1, input),
        instructionPointer = instructionPointer + 2
    )
}

private fun Program.output(): Program {
    val output = memory.read(parameterModes.getOrNull(0), instructionPointer + 1)
    return copy(
        instructionPointer = instructionPointer + 2,
        output = output
    )
}
