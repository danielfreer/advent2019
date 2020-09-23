package intcode

typealias Memory = List<Int>

fun memory(vararg code: Int) = code.toList()

fun Memory.read(mode: Int?, address: Int) = when (mode) {
    0, null -> readPosition(address)
    1 -> readImmediate(address)
    else -> throw IllegalArgumentException("Unknown mode: $mode")
}

fun Memory.readPosition(address: Int) = get(get(address))
fun Memory.readImmediate(address: Int) = get(address)

fun Memory.write(mode: Int?, address: Int, result: Int) = when (mode) {
    0, null -> writePosition(address, result)
    1 -> writeImmediate(address, result)
    else -> throw IllegalArgumentException("Unknown mode: $mode")
}

fun Memory.writePosition(address: Int, result: Int) = mapIndexed(transform(get(address), result))
fun Memory.writeImmediate(address: Int, result: Int) = mapIndexed(transform(address, result))

private fun transform(address: Int, result: Int): (Int, Int) -> Int = { index, i ->
    if (index == address) result else i
}

fun Memory.writeAllImmediate(parameters: Map<Int, Int>) = mapIndexed { index, i ->
    parameters[index] ?: i
}
