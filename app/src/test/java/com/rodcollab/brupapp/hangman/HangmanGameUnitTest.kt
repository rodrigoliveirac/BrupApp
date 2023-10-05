package com.rodcollab.brupapp.hangman

import com.rodcollab.brupapp.hangman.domain.Trial
import com.rodcollab.brupapp.hangman.ui.alphabet
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class HangmanGameUnitTest {

    private val questionStorage: ArrayList<Char> = arrayListOf('h', 'i')

    private lateinit var defaultGame: HangmanGameImplFake

    @Before
    fun setup() {
        defaultGame = HangmanGameImplFake(questionStorage)
    }

    @Test
    fun verifyAnswerThenReturnNewTrialState() {

        val letter = alphabet.random()

        defaultGame.verifyAnswerThenUpdateGameState(letter)

        val trialExpected = defaultGame.getTrialState()

        val trialActual = Trial(
            chars = questionStorage,
            usedLetters = defaultGame.guessedLetters,
            chances = defaultGame.chances,
            tries = defaultGame.tries,
            hits = defaultGame.hits,
            errors = defaultGame.errors,
            answer = defaultGame.answer
        )

        assertTrue(trialActual == trialExpected)
    }


    @Test
    fun testIncrementTries() {

        // Get initial value of the tries counter
        val initialTryCount = defaultGame.tries

        // Perform the first try
        defaultGame.verifyAnswerThenUpdateGameState('h')

        // Verify if the counter has been incremented correctly after the second try
        assertEquals(initialTryCount + 1, defaultGame.tries)

        // Perform the second try
        defaultGame.verifyAnswerThenUpdateGameState('h')

        // Verify if the counter has been incremented correctly after the second try
        assertEquals(initialTryCount + 2, defaultGame.tries)

    }

    @Test
    fun checkGameStateWhenUserGuessesWrongLetter() {

        val letterExists = defaultGame.isLetterExists('l', questionStorage)
        assertEquals(false, letterExists)

        val initialChances = defaultGame.chances

        defaultGame.updateScore(letterExists)
        assertEquals(0, defaultGame.hits)
        assertEquals(1, defaultGame.errors)

        assertEquals(initialChances - 1, defaultGame.chances)

    }

    @Test
    fun checkGameStateWhenUserGuessesHitLetter() {

        // Check if the letter guessed by the user exist in the right answer
        val letterExists = defaultGame.isLetterExists('h', questionStorage)
        assertEquals(true, letterExists)

        // Update score
        defaultGame.updateScore(letterExists)

        assertEquals(1, defaultGame.hits)
        assertEquals(0, defaultGame.errors)
        assertEquals(6, defaultGame.chances)
    }

    @Test
    fun testGetAnswerReturnsCorrectSequence() {

        val questionStorage = mutableListOf<Char>('c','o','d','e')

        val answer = defaultGame.getAnswer(questionStorage)

        assertEquals("code", answer)

    }
}
