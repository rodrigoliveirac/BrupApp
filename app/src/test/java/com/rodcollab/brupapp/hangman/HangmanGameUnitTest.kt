package com.rodcollab.brupapp.hangman

import com.rodcollab.brupapp.data.NetworkRandomWordsImpl
import com.rodcollab.brupapp.hangman.domain.Trial
import com.rodcollab.brupapp.hangman.ui.alphabet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HangmanGameUnitTest {

    private lateinit var defaultGame: HangmanGameImplFake

    @Before
    fun setup()  {
        NetworkRandomWordsImpl.getInstance()
        val randomWordsNetwork = NetworkRandomWordsImpl.getInstance()
        defaultGame = HangmanGameImplFake(randomWordsNetwork)
    }
    @After
    fun prepareGame() = runTest {
        CoroutineScope(Dispatchers.Unconfined).launch {
            defaultGame.prepareGame()
        }
    }

    @Test
    fun verifyAnswerThenReturnNewTrialState() {

        val letter = alphabet.random()

        val initialTrial = defaultGame.gameState()

        defaultGame.verifyAnswerThenUpdateGameState(letter)

        val trialExpected = defaultGame.gameState()

        val trialActual = Trial(
            gameOn = defaultGame.gameOn,
            gameOver = defaultGame.gameOver,
            chars = defaultGame.sourceAnswer,
            chances = defaultGame.chances,
            tries = defaultGame.tries,
            hits = defaultGame.hits,
            errors = defaultGame.errors,
            answer = defaultGame.answer,
            usedLetters = defaultGame.usedLetters,
            tip = defaultGame.tip,
            gameIsFinish = defaultGame.gameIsFinish,
            newGame = defaultGame.newGame,
            performance = defaultGame.rightAnswers.toFloat() / defaultGame.totalWords.toFloat()
        )

        assertTrue(trialActual == trialExpected)
        assertFalse(trialActual == initialTrial)
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

        val sourceAnswerMock = listOf<Char>('h','i')

        val letterExists = defaultGame.isLetterExists('l', sourceAnswerMock)
        assertEquals(false, letterExists)

        val initialChances = defaultGame.chances

        defaultGame.updateScore(letterExists)
        assertEquals(0, defaultGame.hits)
        assertEquals(1, defaultGame.errors)

        assertEquals(initialChances - 1, defaultGame.chances)

    }

    @Test
    fun checkGameStateWhenUserGuessesHitLetter() {

        val sourceAnswerMock = listOf<Char>('h','i')

        // Check if the letter guessed by the user exist in the right answer
        val letterExists = defaultGame.isLetterExists('h', sourceAnswerMock)


       assertEquals(true, letterExists)

        // Update score
        defaultGame.updateScore(letterExists)

        assertEquals(1, defaultGame.hits)
        assertEquals(0, defaultGame.errors)
        assertEquals(6, defaultGame.chances)
    }

}
