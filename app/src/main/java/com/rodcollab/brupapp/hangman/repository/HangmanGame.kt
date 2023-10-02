package com.rodcollab.brupapp.hangman.repository

import com.rodcollab.brupapp.hangman.domain.Trial

interface HangmanGame {

    fun getTrialState(): Trial

    fun verifyAnswerThenUpdateGameState(letter: Char)

    fun guessedLetters(): List<Char>

    fun getAnswerListChar(): List<Char>
}

class HangmanGameImpl(private val source: List<Char>) : HangmanGame {

    var guessedLetters = mutableListOf<Char>()
        private set

    var hits = 0
        private set

    var tries = 0
        private set

    var errors = 0
        private set

    var chances = 6
        private set

    var answer = getAnswer(source)
        private set

    fun getAnswer(questionStorage: List<Char>): String {
        var answer = ""
        questionStorage.forEach {
            answer += it.toString()
        }
        return answer
    }

    override fun getTrialState(): Trial = Trial(
        chars = source,
        chances = chances,
        tries = tries,
        hits = hits,
        errors = errors,
        answer = answer,
        guessedLetters = guessedLetters
    )


    override fun verifyAnswerThenUpdateGameState(letter: Char) {

        incrementTries()

        val letterExists = isLetterExists(letter, source)

        updateScore(letterExists)

        addToGuessedLetters(letter)

    }

    override fun guessedLetters(): List<Char> = guessedLetters

    override fun getAnswerListChar(): List<Char> = source

    fun incrementTries() {
        tries += 1
    }

    fun isLetterExists(letter: Char, questionStorage: List<Char>): Boolean {
        return questionStorage.any { it == letter }
    }

    fun updateScore(letterExists: Boolean) {
        if (letterExists) {
            hits += 1
        } else {
            errors += 1
            chances -= 1
        }
    }

    fun addToGuessedLetters(letter: Char) {
        guessedLetters.add(letter)
    }
}