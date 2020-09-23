package intcode

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class ComputerTest : DescribeSpec({
    data class Case(val program: Program, val expected: Memory)

    val cases = listOf(
        Case(
            program(1, 0, 0, 0, 99),
            memory(2, 0, 0, 0, 99)
        ),
        Case(
            program(2, 3, 0, 3, 99),
            memory(2, 3, 0, 6, 99)
        ),
        Case(
            program(2, 4, 4, 5, 99, 0),
            memory(2, 4, 4, 5, 99, 9801)
        ),
        Case(
            program(1, 1, 1, 4, 99, 5, 6, 0, 99),
            memory(30, 1, 1, 4, 2, 5, 6, 0, 99)
        ),
        Case(
            program(1002, 4, 3, 4, 33),
            memory(1002, 4, 3, 4, 99)
        )
    )
    describe("execute") {
        cases.forEach { (program, expected) ->
            it("when program is ${program.memory}, memory should contain exactly $expected") {
                val memory = execute(program).first
                memory.shouldContainExactly(expected)
            }
        }
        it("when program is [3,0,4,0,99], output should be the same as input") {
            (0..99).forEach { input ->
                val output = execute(Program(listOf(3, 0, 4, 0, 99), 0, input, output = null)).second
                output.shouldBe(input)
            }
        }
    }
})
