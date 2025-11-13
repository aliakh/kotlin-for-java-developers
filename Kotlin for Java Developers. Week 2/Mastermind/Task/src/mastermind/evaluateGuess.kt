package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val secretArray = secret.toCharArray()
    val guessArray = guess.toCharArray()

    val RIGHT_POSITION = 'R'
    val WRONG_POSITION = 'W'

    secretArray.forEachIndexed { i, c ->
        if (guessArray[i] == c) {
            secretArray[i] = RIGHT_POSITION
            guessArray[i] = RIGHT_POSITION
        }
    }

    secretArray.forEachIndexed { i, c ->
        if (c != RIGHT_POSITION) {
            val index = guessArray.indexOf(c)
            if (index != -1) {
                secretArray[i] = WRONG_POSITION
                guessArray[index] = WRONG_POSITION
            }
        }
    }

    val rightPosition = secretArray.count { it == RIGHT_POSITION }
    val wrongPosition = secretArray.count { it == WRONG_POSITION }

    return Evaluation(rightPosition, wrongPosition)
}
