package intcode

typealias Program = Pair<Memory, Int>

val Program.memory get() = first
val Program.instructionPointer get() = second
val Program.opCode get() = memory.read(instructionPointer)
val Program.firstParameter get() = memory.read(instructionPointer + 1)
val Program.secondParameter get() = memory.read(instructionPointer + 2)
val Program.thirdParameter get() = memory.read(instructionPointer + 3)

fun program(vararg code: Int) = code.toList() to 0
fun Program.modify(
    memory: Memory = first,
    instructionPointer: Int = second
) = Program(memory, instructionPointer)
