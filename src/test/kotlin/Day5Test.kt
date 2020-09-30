import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class Day5Test : DescribeSpec({
    describe("opcode 3") {
        it("when memory is [3,0,4,0,99], output should be the same as input") {
            (0..99).forEach { input ->
                intcode.execute(listOf(3, 0, 4, 0, 99), input).shouldBe(input)
            }
        }
    }
    describe("parameter modes") {
        listOf(
            listOf(1002, 4, 3, 4, 33) to listOf(1002, 4, 3, 4, 99),
            listOf(1101, 100, -1, 4, 0) to listOf(1101, 100, -1, 4, 99)
        ).forEach { (memory, expected) ->
            it("when memory is $memory, should contain exactly $expected") {
                intcode.execute(memory).shouldContainExactly(expected)
            }
        }
    }
    describe("opcode 8") {
        listOf(
            7 to false,
            8 to true,
            9 to false
        ).forEach { (input, equal) ->
            val expected = if (equal) 1 else 0
            val condition = if (equal) " equal" else " not equal"
            it("using position mode, $input should $condition 8") {
                val memory = listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
                intcode.execute(memory, input).shouldBe(expected)
            }
            it("using immediate mode, $input should $condition 8") {
                val memory = listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
                intcode.execute(memory, input).shouldBe(expected)
            }
        }
    }
    describe("opcode 7") {
        listOf(
            7 to true,
            8 to false,
            9 to false
        ).forEach { (input, lessThan) ->
            val expected = if (lessThan) 1 else 0
            val condition = if (lessThan) " be less than" else " not be less than"
            it("using position mode, $input should $condition 8") {
                val memory = listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
                intcode.execute(memory, input).shouldBe(expected)
            }
            it("using immediate mode, $input should $condition 8") {
                val memory = listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
                intcode.execute(memory, input).shouldBe(expected)
            }
        }
    }
    describe("opcode 6") {
        listOf(
            0 to true,
            1 to false
        ).forEach { (input, equalsZero) ->
            val expected = if (equalsZero) 0 else 1
            val condition = if (equalsZero) " equal" else " not equal"
            it("using position mode, $input should $condition 0") {
                val memory = listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
                intcode.execute(memory, input).shouldBe(expected)
            }
        }
    }
    describe("opcode 5") {
        listOf(
            0 to true,
            1 to false
        ).forEach { (input, equalsZero) ->
            val expected = if (equalsZero) 0 else 1
            val condition = if (equalsZero) " equal" else " not equal"
            it("using immediate mode, $input should $condition 0") {
                val memory = listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
                intcode.execute(memory, input).shouldBe(expected)
            }
        }
    }
    describe("opcodes 5-8") {
        listOf(
            6 to 999,
            7 to 999,
            8 to 1000,
            9 to 1001,
            10 to 1001
        ).forEach { (input, expected) ->
            val condition = when (expected) {
                999 -> " be less than"
                1000 -> " equal to"
                1001 -> " be greater than"
                else -> throw IllegalArgumentException("Bad test input")
            }
            val memory = listOf(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
            )
            it("$input should $condition 8") {
                intcode.execute(memory, input).shouldBe(expected)
            }
        }
    }
})
