package com.rodcollab.brupapp.hangman.repository

import com.rodcollab.brupapp.data.NetworkRandomWords
import com.rodcollab.brupapp.data.NetworkRandomWordsImpl
import com.rodcollab.brupapp.hangman.domain.Trial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface HangmanGame {
    suspend fun prepareGame()
    fun verifyAnswerThenUpdateGameState(letter: Char)
    suspend fun resetGame()
    fun gameState(): Trial
}

data class WordAnswer(
    val value: String = "",
    val definition: String = ""
)

class HangmanGameImpl(
    private val dataSource: NetworkRandomWords,
) : HangmanGame {

    private var dataSet = mutableListOf<WordAnswer>()
    var gameIsFinish = false
        private set
    var totalWords = 0
        private set
    var newGame = false
        private set
    var sourceAnswer = listOf<Char>()
        private set
    var answer = ""
        private set
    var tip = ""
        private set

    companion object {

        private var instance: HangmanGame? = null
        fun getInstance(): HangmanGame {
            if (instance == null) {
                instance =
                    HangmanGameImpl(dataSource = NetworkRandomWordsImpl.getInstance())
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
    var rightAnswers = 0
        private set

    override suspend fun prepareGame() {

        dataSet = withContext(Dispatchers.IO) {
            dataSource.questionItems().toMutableList()
        }

        totalWords = dataSet.size

        rightAnswers = dataSet.size

        getSourceAnswer(dataSet) { sourceAnswerCreated ->
            sourceAnswer = sourceAnswerCreated.map { it.lowercase().first() }
        }
    }


    override fun verifyAnswerThenUpdateGameState(letter: Char) {

        addToGuessedLetters(letter)

        incrementTries()

        val letters = sourceAnswer.filter { char -> char.isLetter() }
        gameOn = usedLetters.containsAll(letters)

        val letterExists = isLetterExists(letter, sourceAnswer)

        updateScore(letterExists)

        if (gameOver) {
            rightAnswers -= 1
        }

        if (dataSet.isEmpty() && gameOver || gameOn && dataSet.isEmpty()) {
            gameIsFinish = true
        }

    }

    override suspend fun resetGame() {
        usedLetters.removeAll(usedLetters)
        gameOn = false
        gameOver = false
        chances = 6
        errors = 0
        hits = 0
        tries = 0
        usedLetters = usedLetters

        if (gameIsFinish) {
            prepareGame()
            gameIsFinish = false
        } else {
            getSourceAnswer(dataSet) {
                sourceAnswer = it.map { char ->
                    char.lowercase().first()
                }
            }
        }
    }

    override fun gameState(): Trial = trial()

    private fun trial() = Trial(
        gameOn = gameOn,
        gameOver = gameOver,
        chars = sourceAnswer,
        chances = chances,
        tries = tries,
        hits = hits,
        errors = errors,
        answer = answer,
        usedLetters = usedLetters,
        tip = tip,
        gameIsFinish = gameIsFinish,
        newGame = newGame,
        performance = rightAnswers.toFloat() / totalWords.toFloat()
    )

    private fun getSourceAnswer(
        data: List<WordAnswer>,
        sourceAnswer: (List<Char>) -> Unit
    ) {
        val random = data.random()
        dataSet.remove(random)
        answer = random.value
        tip = random.definition
        val answer = mutableListOf<Char>()
        random.value.forEach { char ->
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
            gameOver = chances == 0
        }
    }

    private fun addToGuessedLetters(letter: Char) {
        usedLetters.add(letter)
        usedLetters = usedLetters
    }
}