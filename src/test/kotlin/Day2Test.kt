import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly

class Day2Test : DescribeSpec({
    data class Case(val program: List<Int>, val expected: List<Int>)

    describe("execute") {
        listOf(
            Case(
                program = listOf(1, 0, 0, 0, 99),
                expected = listOf(2, 0, 0, 0, 99)
            ),
            Case(
                program = listOf(2, 3, 0, 3, 99),
                expected = listOf(2, 3, 0, 6, 99)
            ),
            Case(
                program = listOf(2, 4, 4, 5, 99, 0),
                expected = listOf(2, 4, 4, 5, 99, 9801)
            ),
            Case(
                program = listOf(1, 1, 1, 4, 99, 5, 6, 0, 99),
                expected = listOf(30, 1, 1, 4, 2, 5, 6, 0, 99)
            ),
            Case(
                program = listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50),
                expected = listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50)
            )
        ).forEach { (program, expected) ->
            it("when program is $program, should be $expected") {
                execute(program)
                    .shouldContainExactly(expected)
            }
        }
    }
})
