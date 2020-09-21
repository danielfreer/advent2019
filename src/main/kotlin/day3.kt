import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
fun main(vararg args: String) {
    val paths = args.toList().ifEmpty { loadResource("day3.txt") }

    val (distance, firstDuration) = measureTimedValue { findIntersection(paths) }
    println("Day 3 - Part 1 Solution took: $firstDuration")
    println("Manhattan distance: $distance")
}

data class Point(val x: Int, val y: Int)

fun manhattanDistance(points: Set<Point>): Int {
    return points
        .map { it.x.absoluteValue + it.y.absoluteValue }
        .minOrNull()
        ?: throw IllegalArgumentException("Cannot find min of empty set: $points")
}

private fun Point.moveRight() = copy(x = x + 1)
private fun Point.moveUp() = copy(y = y + 1)
private fun Point.moveLeft() = copy(x = x - 1)
private fun Point.moveDown() = copy(y = y - 1)

private fun movement(direction: Char): (Point) -> Point {
    return when (direction) {
        'R' -> Point::moveRight
        'U' -> Point::moveUp
        'L' -> Point::moveLeft
        'D' -> Point::moveDown
        else -> throw IllegalArgumentException("Unknown direction: $direction")
    }
}

fun findIntersection(paths: List<String>): Int {
    val intersections = paths.map { path ->
        path.split(",")
            .flatMap { movement -> List(movement.drop(1).toInt()) { movement.first().let(::movement) } }
            .runningFold(Point(x = 0, y = 0)) { point, move -> move(point) }
            .drop(1)
            .toSet()
    }.reduce { acc, set -> acc.intersect(set) }
    return manhattanDistance(intersections)
}
