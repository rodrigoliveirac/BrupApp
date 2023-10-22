package com.rodcollab.brupapp.hangman.repository

import com.rodcollab.brupapp.data.NetworkRandomWords
import com.rodcollab.brupapp.data.NetworkRandomWordsImpl
import com.rodcollab.brupapp.hangman.domain.Trial

interface HangmanGame {

    fun getTrialState(): Trial
    fun verifyAnswerThenUpdateGameState(letter: Char)
    fun resetGame()
}

@OptIn(ExperimentalCoroutinesApi::class)
class HangmanGameImpl(
    private val randomWords: NetworkRandomWords,
) : HangmanGame {


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

    var sourceAnswer = getSourceAnswer(dataSet = dataSet)
        private set

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

    var answer = getAnswer(sourceAnswer)
        private set

    private fun getAnswer(questionStorage: List<Char>): String {
        var answer = ""
        questionStorage.forEach {
            answer += it.toString()
        }
        return answer
    }

    override fun getTrialState(): Trial = Trial(
        chars = sourceAnswer,
        chances = chances,
        tries = tries,
        hits = hits,
        errors = errors,
        answer = answer,
        usedLetters = usedLetters
    )


    override fun verifyAnswerThenUpdateGameState(letter: Char) {
        addToGuessedLetters(letter)

        incrementTries()

        val letterExists = isLetterExists(letter, sourceAnswer)

        updateScore(letterExists)

    }

    override fun resetGame() {
        usedLetters.removeAll(usedLetters)
        chances = 6
        errors = 0
        hits = 0
        tries = 0
        usedLetters = usedLetters
        sourceAnswer = getSourceAnswer(dataSet)
        answer = getAnswer(sourceAnswer)
    }

    private fun getSourceAnswer(dataSet: List<String>): List<Char> {
        val random = dataSet.random()
        val answer = mutableListOf<Char>()
        random.forEach {
            answer.add(it)
        }
        return answer
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