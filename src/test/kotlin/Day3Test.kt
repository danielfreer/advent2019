import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day3Test : DescribeSpec({
    describe("manhattanDistance") {
        val points = setOf(Point(3, 3), Point(6, 5))
        it("when points are $points, should be 6") {
            manhattanDistance(points)
                .shouldBe(6)
        }
    }
    data class Case(val paths: List<String>, val expected: Int)
    describe("findIntersection") {
        listOf(
            Case(listOf("R8,U5,L5,D3", "U7,R6,D4,L4"), 6),
            Case(
                listOf(
                    "R75,D30,R83,U83,L12,D49,R71,U7,L72",
                    "U62,R66,U55,R34,D71,R55,D58,R83"
                ),
                159
            ),
            Case(
                listOf(
                    "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                    "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
                ),
                135
            )
        ).forEach { (paths, expected) ->
            it("when paths are $paths, should be $expected") {
                findIntersection(paths)
                    .shouldBe(expected)
            }
        }
    }
    describe("stepsToIntersections") {
        val paths = listOf("R8,U5,L5,D3", "U7,R6,D4,L4")
        val expected = listOf(
            mapOf(Point(x = 3, y = 3) to 20, Point(x = 6, y = 5) to 15),
            mapOf(Point(x = 3, y = 3) to 20, Point(x = 6, y = 5) to 15)
        )
        it("when paths are $paths, should be $expected") {
            findStepsToIntersections(paths)
                .shouldBe(expected)
        }
    }
    describe("findSteps") {
        listOf(
            Case(listOf("R8,U5,L5,D3", "U7,R6,D4,L4"), 30),
            Case(
                listOf(
                    "R75,D30,R83,U83,L12,D49,R71,U7,L72",
                    "U62,R66,U55,R34,D71,R55,D58,R83"
                ),
                610
            ),
            Case(
                listOf(
                    "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                    "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
                ),
                410
            )
        ).forEach { (paths, expected) ->
            it("when paths are $paths, should be $expected") {
                findSteps(paths)
                    .shouldBe(expected)
            }
        }
    }
})
