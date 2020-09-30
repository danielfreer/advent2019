package intcode

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MemoryTest : DescribeSpec({
    data class ReadCase(val memory: Memory, val address: Int, val expected: Any)
    data class WriteCase(val memory: Memory, val address: Int, val value: Int, val expected: Any)

    describe("readPosition") {
        listOf(
            ReadCase(listOf(1, 2, 0), address = 0, 2),
            ReadCase(listOf(1, 2, 0), address = 1, 0),
            ReadCase(listOf(1, 2, 0), address = 2, 1)
        ).forEach { (memory, address, expected) ->
            it("when memory is $memory and address is $address, should be $expected") {
                memory.readPosition(address).shouldBe(expected)
            }
        }
        it("when address is out of bounds, should throw exception") {
            val memory = listOf(0)
            shouldThrow<IndexOutOfBoundsException> { memory.readPosition(1) }
        }
        it("when the position address is out of bounds, should throw exception") {
            val memory = listOf(1)
            shouldThrow<IndexOutOfBoundsException> { memory.readPosition(0) }
        }
    }
    describe("readImmediate") {
        listOf(
            ReadCase(listOf(1, 2, 0), address = 0, 1),
            ReadCase(listOf(1, 2, 0), address = 1, 2),
            ReadCase(listOf(1, 2, 0), address = 2, 0)
        ).forEach { (memory, address, expected) ->
            it("when memory is $memory and address is $address, should be $expected") {
                memory.readImmediate(address).shouldBe(expected)
            }
        }
        it("when address is out of bounds, should throw exception") {
            val memory = listOf(0)
            shouldThrow<IndexOutOfBoundsException> { memory.readImmediate(1) }
        }
    }
    describe("writePosition") {
        listOf(
            WriteCase(listOf(1, 2, 0), address = 0, value = 3, listOf(1, 3, 0)),
            WriteCase(listOf(1, 2, 0), address = 1, value = 3, listOf(1, 2, 3)),
            WriteCase(listOf(1, 2, 0), address = 2, value = 3, listOf(3, 2, 0))
        ).forEach { (memory, address, value, expected) ->
            it("when memory is $memory and value $value is written at address $address, should be $expected") {
                memory.writePosition(address, value).shouldBe(expected)
            }
        }
    }
    describe("writeImmediate") {
        listOf(
            WriteCase(listOf(1, 2, 0), address = 0, value = 3, listOf(3, 2, 0)),
            WriteCase(listOf(1, 2, 0), address = 1, value = 3, listOf(1, 3, 0)),
            WriteCase(listOf(1, 2, 0), address = 2, value = 3, listOf(1, 2, 3))
        ).forEach { (memory, address, value, expected) ->
            it("when memory is $memory and value $value is written at address $address, should be $expected") {
                memory.writeImmediate(address, value).shouldBe(expected)
            }
        }
    }
})
