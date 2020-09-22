import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain

class Day4Test : DescribeSpec({
    describe("possiblePasswords") {
        it("when range includes 111111, should contain 111111") {
            val range = 111111..111111
            possiblePasswords(range)
                .shouldContain(111111)
        }
        it("when range includes 223450, should not contain 223450") {
            val range = 223450..223450
            possiblePasswords(range)
                .shouldNotContain(223450)
        }
        it("when range includes 123789, should not contain 123789") {
            val range = 123789..123789
            possiblePasswords(range)
                .shouldNotContain(123789)
        }
    }
    describe("possiblePasswordsRedux") {
        it("when range includes 112233, should contain 112233") {
            val range = 112233..112233
            possiblePasswordsRedux(range)
                .shouldContain(112233)
        }
        it("when range includes 123444, should not contain 123444") {
            val range = 123444..123444
            possiblePasswordsRedux(range)
                .shouldNotContain(123444)
        }
        it("when range includes 111122, should contain 111122") {
            val range = 111122..111122
            possiblePasswordsRedux(range)
                .shouldContain(111122)
        }
    }
})
