import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
data class Solution(val day: Int, val part: Int, val solution: Any, val duration: Duration)

@ExperimentalTime
fun solve(day: Int, part: Int, func: () -> Any): Solution {
    val (solution, duration) = measureTimedValue { func() }
    return Solution(day, part, solution, duration)
}
