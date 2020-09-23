package intcode

typealias Program = Pair<Memory, Int>

val Program.memory get() = first
val Program.instructionPointer get() = second
val Program.instruction get() = memory.readImmediate(instructionPointer)
val Program.opcode get() = instruction.toList().takeLast(2).toInt()
val Program.parameterModes get() = instruction.toList().dropLast(2).reversed()

fun program(vararg code: Int) = code.toList() to 0
fun Program.modify(
    memory: Memory = first,
    instructionPointer: Int = second
) = Program(memory, instructionPointer)

private fun Int.toList() = toString().map(Char::toString).map(String::toInt)
private fun List<Int>.toInt() = joinToString(separator = "").toInt()
