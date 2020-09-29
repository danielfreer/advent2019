package intcode

typealias Memory = List<Int>

fun Memory.readPosition(address: Int) = get(get(address))
fun Memory.readImmediate(address: Int) = get(address)

fun Memory.writePosition(address: Int, value: Int) = mapIndexed(transform(get(address), value))
fun Memory.writeImmediate(address: Int, value: Int) = mapIndexed(transform(address, value))

private fun <T, R> transform(indexToTransform: T, newValue: R): (T, R) -> R = { index, originalValue ->
    if (index == indexToTransform) newValue else originalValue
}
