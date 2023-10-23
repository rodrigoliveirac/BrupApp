package com.rodcollab.brupapp.hangman.repository

import com.rodcollab.brupapp.data.NetworkRandomWords
import com.rodcollab.brupapp.data.NetworkRandomWordsImpl
import com.rodcollab.brupapp.hangman.domain.Trial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface HangmanGame {
    suspend fun prepareGame(): Trial
    fun verifyAnswerThenUpdateGameState(letter: Char)
    fun resetGame()
    fun gameState(): Trial
}
class HangmanGameImpl(
    private val randomWords: NetworkRandomWords,
) : HangmanGame {

    private var dataSet = mutableListOf<String>()
    var sourceAnswer = listOf<Char>()
        private set

    var answer = ""
        private set

    companion object {

        private var instance: HangmanGame? = null
        fun getInstance(): HangmanGame {
            if (instance == null) {
                instance =
                    HangmanGameImpl(randomWords = NetworkRandomWordsImpl.getInstance())
            }
            return instance!!
        }
    }

    var usedLetters = mutableListOf<Char>()
        private set

    var hits = 0
        private set

    var tries = 0
        private set

    var errors = 0
        private set

    var chances = 6
        private set

    var gameOver = false
        private set
    var gameOn = false
        private set

    private fun getAnswer(questionStorage: List<Char>, answerCreated: (String) -> Unit) {
        var answer = ""
        questionStorage.forEach {
            answer += it.toString()
        }
        answerCreated(answer)
    }

    override suspend fun prepareGame(): Trial {

        dataSet = withContext(Dispatchers.IO) {
            randomWords.randomWords().map { word -> word.word }.toMutableList()
        }
        getSourceAnswer(dataSet) { sourceAnswerCreated ->
            sourceAnswer = sourceAnswerCreated
        }
        getAnswer(sourceAnswer) { answerCreated ->
            answer = answerCreated
        }

        return Trial(
            gameOn = gameOn,
            gameOver = gameOver,
            chars = sourceAnswer,
            chances = chances,
            tries = tries,
            hits = hits,
            errors = errors,
            answer = answer,
            usedLetters = usedLetters
        )
    }


    override fun verifyAnswerThenUpdateGameState(letter: Char) {
        addToGuessedLetters(letter)

        incrementTries()

        val letterExists = isLetterExists(letter, sourceAnswer)

        gameOn = usedLetters.containsAll(sourceAnswer)
        gameOver = chances == 1

        updateScore(letterExists)

    }

    override fun resetGame() {
        usedLetters.removeAll(usedLetters)
        gameOn = false
        gameOver = false
        chances = 6
        errors = 0
        hits = 0
        tries = 0
        usedLetters = usedLetters
        getSourceAnswer(dataSet) {
            sourceAnswer = it
        }
        getAnswer(sourceAnswer) {
            answer = it
        }
    }

    override fun gameState(): Trial {

        return Trial(
            gameOn = gameOn,
            gameOver = gameOver,
            chars = sourceAnswer,
            chances = chances,
            tries = tries,
            hits = hits,
            errors = errors,
            answer = answer,
            usedLetters = usedLetters
        )
    }

    private fun getSourceAnswer(data: List<String>, sourceAnswer: (List<Char>) -> Unit) {
        val random = data.random()
        dataSet.remove(random)
        val answer = mutableListOf<Char>()
        random.forEach { char ->
            answer.add(char)
        }
        sourceAnswer(answer)
    }

    private fun incrementTries() {
        tries += 1
    }

    private fun isLetterExists(letter: Char, questionStorage: List<Char>): Boolean {
        return questionStorage.any { it == letter }
    }

    private fun updateScore(letterExists: Boolean) {
        if (letterExists) {
            hits += 1
        } else {
            errors += 1
            chances -= 1
        }
    }

    private fun addToGuessedLetters(letter: Char) {
        usedLetters.add(letter)
        usedLetters = usedLetters
    }
}