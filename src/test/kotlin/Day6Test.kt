
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day6Test : DescribeSpec({
    @Suppress("Reformat")
    val orbitMap = listOf(
                 // Direct | Indirect
        "COM)B", //      1          0
        "B)C",   //      1          1
        "C)D",   //      1          2
        "D)E",   //      1          3
        "E)F",   //      1          4
        "B)G",   //      1          1
        "G)H",   //      1          2
        "D)I",   //      1          3
        "E)J",   //      1          4
        "J)K",   //      1          5
        "K)L"    //      1          6
              // Total: 11         31
    )

    describe("countDirectOrbits") {
        it("given $orbitMap, should be 11") {
            parseRootNode(orbitMap)
                .let(::countDirectOrbits)
                .shouldBe(11)
        }
    }
    describe("countIndirectOrbits") {
        it("given $orbitMap, should be 31") {
            parseRootNode(orbitMap)
                .let(::countIndirectOrbits)
                .shouldBe(31)
        }
    }
    describe("countTotalOrbits") {
        it("given $orbitMap, should be 42") {
            countTotalOrbits(orbitMap).shouldBe(42)
        }
    }
})
