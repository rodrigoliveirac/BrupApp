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

data class WordAnswer(
    val value: String = "",
    val definition: String = ""
)

class HangmanGameImpl(
    private val randomWords: NetworkRandomWords,
) : HangmanGame {

    private var dataSet = mutableListOf<WordAnswer>()
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

    override suspend fun prepareGame(): Trial {

        dataSet = withContext(Dispatchers.IO) {
            randomWords.randomWords().map { word -> word.word }.map { word ->
                //TODO("Need to review")
                val definition = randomWords.definition(word)
                var result = ""
                if (definition == null) {
                    val wordCapitalized = word.replaceFirstChar { it.uppercase() }
                    val definitionUsingWordCapitalized = randomWords.definition(wordCapitalized)
                    definitionUsingWordCapitalized?.let { response ->
                        result = if (response.isNotEmpty()) {
                            var text = response.first().text.toString()
                            if (text.contains("<xref>")) {
                                val newText = text.split("<xref>").toMutableList()
                                text = newText.first()
                                newText.remove(newText.first())
                                newText.map {
                                    val nText = it.split("</xref>").toMutableList()
                                    text += nText.first() + nText.last()
                                }
                                text
                            } else if (text.contains("<em>")) {
                                text
                            } else {
                                text
                            }
                        } else {
                            result
                        }
                    }
                } else {
                    result = if (definition.isNotEmpty()) {
                        var text = definition.first().text.toString()
                        if (text.contains("<xref>")) {
                            val newText = text.split("<xref>").toMutableList()
                            text = newText.first()
                            newText.remove(newText.first())
                            newText.map {
                                val nText = it.split("</xref>").toMutableList()
                                text += nText.first() + nText.last()
                            }
                            text
                        } else if (text.contains("<em>")) {
                            text
                        } else {
                            text
                        }
                    } else {
                        result
                    }
                }
                WordAnswer(value = word, definition = result)
            }.toMutableList()
        }

        getSourceAnswer(dataSet) { sourceAnswerCreated ->
            sourceAnswer = sourceAnswerCreated
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
            usedLetters = usedLetters,
            tip = tip
        )
    }


    override fun verifyAnswerThenUpdateGameState(letter: Char) {
        addToGuessedLetters(letter)

        incrementTries()

        gameOn = usedLetters.containsAll(sourceAnswer)

        val letterExists = isLetterExists(letter, sourceAnswer)

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
            usedLetters = usedLetters,
            tip = tip
        )
    }

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