package intcode

typealias Memory = List<Int>

fun memory(vararg code: Int) = code.toList()
fun Memory.read(address: Int) = get(address)

fun Memory.write(address: Int, result: Int) = mapIndexed { index, i ->
    if (index == address) result else i
}

fun Memory.writeAll(parameters: Map<Int, Int>) = mapIndexed { index, i ->
    parameters[index] ?: i
}
