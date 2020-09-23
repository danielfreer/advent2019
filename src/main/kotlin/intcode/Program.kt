package intcode

data class Program(val memory: Memory, val instructionPointer: Int, val input: Int?, val output: Int?) {
    val instruction by lazy { memory.readImmediate(instructionPointer) }
    val opcode by lazy { instruction.toList().takeLast(2).toInt() }
    val parameterModes by lazy { instruction.toList().dropLast(2).reversed() }
}

fun program(vararg code: Int) = Program(code.toList(), 0, input = null, output = null)

private fun Int.toList() = toString().map(Char::toString).map(String::toInt)
private fun List<Int>.toInt() = joinToString(separator = "").toInt()
