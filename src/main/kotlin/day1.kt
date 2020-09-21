fun main(vararg args: String) {
    val input = args.toList().ifEmpty { loadResource("day1.txt") }
    val modulesMass = input.map(String::toInt)

    val fuel = sumFuelRequired(modulesMass)
    println("Day 1 - Part 1 Solution")
    println("Sum of fuel required: $fuel")
    println()
    val allFuel = sumAllFuelRequired(modulesMass)
    println("Day 1 - Part 2 Solution")
    println("Sum of all fuel required: $allFuel")
}

fun fuelRequired(mass: Int) = mass / 3 - 2

fun sumFuelRequired(modulesMass: List<Int>) = modulesMass.sumBy(::fuelRequired)

tailrec fun allFuelRequired(mass: Int, totalFuel: Int = 0): Int {
    if (mass < 9) return totalFuel
    val fuel = fuelRequired(mass)
    return allFuelRequired(fuel, totalFuel + fuel)
}

fun sumAllFuelRequired(modulesMass: List<Int>) = modulesMass.sumBy(::allFuelRequired)
