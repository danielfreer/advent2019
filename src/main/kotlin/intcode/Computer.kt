package intcode

private const val ADD = 1
private const val MULTIPLY = 2
private const val HALT = 99

fun execute(program: Program, vararg parameters: Pair<Int, Int>): Int {
    val modifiedProgram = program.modify(memory = program.memory.writeAllImmediate(parameters.toMap()))
    val result = execute(modifiedProgram)
    return result[0]
}

tailrec fun execute(program: Program): Memory {
    return when (val opcode = program.opcode) {
        ADD -> execute(program.add())
        MULTIPLY -> execute(program.multiply())
        HALT -> program.memory
        else -> throw IllegalStateException("Unknown opcode: $opcode")
    }
}

private fun Program.add(): Program {
    val firstInput = memory.read(parameterModes.getOrNull(0), instructionPointer + 1)
    val secondInput = memory.read(parameterModes.getOrNull(1), instructionPointer + 2)
    val result = firstInput + secondInput
    return modify(
        memory = memory.write(parameterModes.getOrNull(2), instructionPointer + 3, result),
        instructionPointer = instructionPointer + 4
    )
}

private fun Program.multiply(): Program {
    val firstInput = memory.read(parameterModes.getOrNull(0), instructionPointer + 1)
    val secondInput = memory.read(parameterModes.getOrNull(1), instructionPointer + 2)
    val result = firstInput * secondInput
    return modify(
        memory = memory.write(parameterModes.getOrNull(2), instructionPointer + 3, result),
        instructionPointer = instructionPointer + 4
    )
}
