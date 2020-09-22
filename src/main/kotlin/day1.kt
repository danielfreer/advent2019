import kotlin.time.ExperimentalTime

@ExperimentalTime
fun day1(input: List<String>): List<Solution> {
    val modulesMass = input.map(String::toInt)

    return listOf(
        solve(1, 1) { sumFuelRequired(modulesMass) },
        solve(1, 2) { sumAllFuelRequired(modulesMass) }
    )
}

fun fuelRequired(mass: Int) = mass / 3 - 2

fun sumFuelRequired(modulesMass: List<Int>) = modulesMass.sumBy(::fuelRequired)

tailrec fun allFuelRequired(mass: Int, totalFuel: Int = 0): Int {
    if (mass < 9) return totalFuel
    val fuel = fuelRequired(mass)
    return allFuelRequired(fuel, totalFuel + fuel)
}

fun sumAllFuelRequired(modulesMass: List<Int>) = modulesMass.sumBy(::allFuelRequired)
