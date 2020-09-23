package intcode

private const val ADD = 1
private const val MULTIPLY = 2
private const val HALT = 99

fun execute(program: Program, vararg parameters: Pair<Int, Int>): Int {
    val modifiedProgram = program.modify(memory = program.memory.writeAll(parameters.toMap()))
    val result = execute(modifiedProgram)
    return result[0]
}

tailrec fun execute(program: Program): Memory {
    return when (program.opCode) {
        ADD -> execute(program.add())
        MULTIPLY -> execute(program.multiply())
        HALT -> program.memory
        else -> throw IllegalStateException("Unknown opcode: ${program.opCode}")
    }
}

private fun Program.add(): Program {
    val firstInput = memory.read(firstParameter)
    val secondInput = memory.read(secondParameter)
    return modify(
        memory = memory.write(thirdParameter, result = firstInput + secondInput),
        instructionPointer = instructionPointer + 4
    )
}

private fun Program.multiply(): Program {
    val firstInput = memory.read(firstParameter)
    val secondInput = memory.read(secondParameter)
    return modify(
        memory = memory.write(thirdParameter, result = firstInput * secondInput),
        instructionPointer = instructionPointer + 4
    )
}
