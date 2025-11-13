package nicestring

fun String.isNice(): Boolean {
    return listOf(
        !(contains("bu") || contains("ba") || contains("be")),
        count { it in "aeiou" } >= 3,
        zipWithNext().any { it.first == it.second }
    ).count { it } >= 2
}