import kotlin.time.ExperimentalTime

@ExperimentalTime
fun day4(input: List<String>): List<Solution> {
    val range = input.first().split("-").map(String::toInt).let { it.first()..it.last() }

    return listOf(
        solve(4, 1) { possiblePasswords(range).size },
        solve(4, 2) { possiblePasswordsRedux(range).size }
    )
}

private typealias Rule<T> = (T) -> Boolean

private fun Int.toList() = toString().map(Char::toString).map(String::toInt)

@Suppress("SameParameterValue")
private fun isLength(length: Int): Rule<Int> = { it.toString().length == length }
private fun isWithinRange(range: IntRange): Rule<Int> = { it in range }
private val isTwoAdjacentDigitsTheSame: Rule<Int> = {
    var isTwoAdjacentDigitsTheSame = false
    it.toList().reduce { acc, i ->
        if (acc == i) isTwoAdjacentDigitsTheSame = true
        i
    }
    isTwoAdjacentDigitsTheSame
}
private val isIncreasingOrSame: Rule<Int> = {
    var isIncreasingOrSame = true
    it.toList().reduce { acc, i ->
        if (i < acc) isIncreasingOrSame = false
        i
    }
    isIncreasingOrSame
}

fun possiblePasswords(range: IntRange): Set<Int> {
    return range.asSequence()
        .filter(isLength(6))
        .filter(isWithinRange(range))
        .filter(isTwoAdjacentDigitsTheSame)
        .filter(isIncreasingOrSame)
        .toSet()
}

private val hasExactlyTwoAdjacentDigitsTheSame: Rule<Int> = { password ->
    val adjacentDigitsCounts = mutableMapOf<Int, Int>()
    password.toList().reduce { acc, i ->
        if (acc == i) adjacentDigitsCounts.compute(i) { _, value -> value?.inc() ?: 2 }
        i
    }
    adjacentDigitsCounts.values.any { it == 2 }
}

fun possiblePasswordsRedux(range: IntRange): Set<Int> {
    return range.asSequence()
        .filter(isLength(6))
        .filter(isWithinRange(range))
        .filter(hasExactlyTwoAdjacentDigitsTheSame)
        .filter(isIncreasingOrSame)
        .toSet()
}
