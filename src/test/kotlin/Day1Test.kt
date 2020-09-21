import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day1Test : DescribeSpec({
    data class Case(val mass: Int, val expected: Int)

    describe("fuelRequired") {
        listOf(
            Case(mass = 12, expected = 2),
            Case(mass = 14, expected = 2),
            Case(mass = 1_969, expected = 654),
            Case(mass = 100_756, expected = 33_583)
        ).forEach { (mass, expected) ->
            it("when mass is $mass, should be $expected") {
                fuelRequired(mass)
                    .shouldBe(expected)
            }
        }
    }
    describe("allFuelRequired") {
        listOf(
            Case(mass = 14, expected = 2),
            Case(mass = 1_969, expected = 966),
            Case(mass = 100_756, expected = 50_346)
        ).forEach { (mass, expected) ->
            it("when mass is $mass, should be $expected") {
                allFuelRequired(mass)
                    .shouldBe(expected)
            }
        }
    }
})
