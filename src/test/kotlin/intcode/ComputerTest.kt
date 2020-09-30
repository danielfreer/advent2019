package intcode

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly

class ComputerTest : DescribeSpec({
    data class Case(val memory: Memory, val expected: Memory)

    val cases = listOf(
        Case(
            listOf(1, 0, 0, 0, 99),
            listOf(2, 0, 0, 0, 99)
        ),
        Case(
            listOf(2, 3, 0, 3, 99),
            listOf(2, 3, 0, 6, 99)
        ),
        Case(
            listOf(2, 4, 4, 5, 99, 0),
            listOf(2, 4, 4, 5, 99, 9801)
        ),
        Case(
            listOf(1, 1, 1, 4, 99, 5, 6, 0, 99),
            listOf(30, 1, 1, 4, 2, 5, 6, 0, 99)
        ),
        Case(
            listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50),
            listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50)
        )
    )
    describe("execute") {
        cases.forEach { (memory, expected) ->
            it("when memory is $memory, should contain exactly $expected") {
                execute(memory).shouldContainExactly(expected)
            }
        }
    }
})
