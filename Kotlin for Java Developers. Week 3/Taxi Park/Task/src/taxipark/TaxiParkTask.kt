package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filter { driver ->
        !trips
            .map { trip -> trip.driver }
            .toSet()
            .contains(driver)
    }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count { trip ->
            passenger in trip.passengers
        } >= minTrips
    }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.count { trip ->
            trip.driver == driver && passenger in trip.passengers
        } > 1
    }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.filter { passenger ->
        val withDiscounts = trips.count { passenger in it.passengers && it.discount != null }
        val withoutDiscounts = trips.count { passenger in it.passengers && it.discount == null }
        withDiscounts > withoutDiscounts
    }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? =
    trips.groupBy { trip ->
        val start = trip.duration / 10 * 10
        val end = start + 10
        start.rangeTo(end - 1)
    }.maxByOrNull { rangeToTrips -> rangeToTrips.value.size }?.key

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) {
        return false
    }

    val bestDriversCosts = trips
        .groupBy { trip -> trip.driver }
        .map { (_, tripsByDriver) -> tripsByDriver.sumOf { trip -> trip.cost } }
        .sortedByDescending { costsByDriver -> costsByDriver }
        .take((allDrivers.size * 0.2).toInt())
        .sumOf { costsByDriver -> costsByDriver }

    val totalCosts = trips.sumOf { trip -> trip.cost }

    return bestDriversCosts / totalCosts >= 0.8
}