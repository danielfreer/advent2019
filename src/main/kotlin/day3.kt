import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun day3(paths: List<String>): List<Solution> {
    return listOf(
        solve(3, 1) { findIntersection(paths) },
        solve(3, 2) { findSteps(paths) }
    )
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

private fun calculatePointsTraveled(path: String): List<Point> {
    val startingPoint = Point(x = 0, y = 0)
    return path.split(",")
        .flatMap {
            val distance = it.drop(1).toInt()
            val movement = it.first().let(::movement)
            List(distance) {
                movement
            }
        }
        .runningFold(startingPoint) { point, move -> move(point) }
}

private fun ignoreStartingPoint(points: List<Point>) = points.drop(1)

fun findIntersection(paths: List<String>): Int {
    val intersections = paths
        .map(::calculatePointsTraveled)
        .map(::ignoreStartingPoint)
        .map { it.toSet() }
        .reduce { acc, points -> acc.intersect(points) }
    return manhattanDistance(intersections)
}

fun findStepsToIntersections(paths: List<String>): List<Map<Point, Int>> {
    val allPointsTraveled = paths
        .map(::calculatePointsTraveled)
    val intersections = allPointsTraveled
        .map(::ignoreStartingPoint)
        .map { it.toSet() }
        .reduce { acc, points -> acc.intersect(points) }
    return allPointsTraveled.map { pointsTraveled ->
        intersections.associateWith { intersection -> pointsTraveled.indexOf(intersection) }
    }
}

fun findSteps(paths: List<String>): Int {
    val stepsToIntersections = findStepsToIntersections(paths)
    val intersectionsToSteps = stepsToIntersections
        .reduce { acc, map ->
            val newMap = acc.toMutableMap()
            map.forEach { (point, steps) ->
                newMap.compute(point) { _, value ->
                    value?.plus(steps) ?: steps
                }
            }
            newMap
        }
    return intersectionsToSteps
        .minByOrNull { it.value }
        ?.value ?: throw IllegalArgumentException("Empty collection")
}
