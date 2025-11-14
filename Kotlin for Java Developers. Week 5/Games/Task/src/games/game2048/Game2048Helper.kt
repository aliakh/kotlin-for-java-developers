package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    // get all filled cells
    val filledCells = filterNotNull().toMutableList()

    var i = 1
    while (i < filledCells.size) {
        if (filledCells[i] == filledCells[i - 1]) {
            // merge a cell with the previous cell
            filledCells[i - 1] = merge(filledCells[i])
            filledCells.removeAt(i)
        }
        i++
    }

    return filledCells
}
