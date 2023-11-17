package com.rodcollab.brupapp.hangman.ui

import androidx.compose.runtime.Composable
import com.rodcollab.brupapp.di.ConnectionObserver.hasConnection
import com.rodcollab.brupapp.hangman.domain.Trial
import com.rodcollab.brupapp.hangman.repository.AnswerModel
import com.rodcollab.brupapp.hangman.repository.HangmanGame
import com.rodcollab.brupapp.hangman.repository.HangmanGameImpl
import com.rodcollab.brupapp.hangman.repository.ReviewAnswer
import com.rodcollab.brupapp.hangman.repository.ReviewAnswerImpl
import com.rodcollab.brupapp.hangman.ui.enums.GameMoveForward
import com.rodcollab.brupapp.hangman.ui.enums.GameState
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent
import com.rodcollab.brupapp.mapper.toExternal
import com.rodcollab.brupapp.util.initializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HangmanGameViewModel(
    private val repository: HangmanGame,
    private val reviewAnswer: ReviewAnswer
) : CoroutineScope by MainScope() {

    private val _uiState: MutableStateFlow<HangmanGameUiState> by lazy {
        MutableStateFlow(HangmanGameUiState(gameState = GameState.MENU))
    }
    val uiState = _uiState.asStateFlow()

    private var letters = alphabet.toLetterModels().toMutableList()

    fun verifyAnswerThenUpdateGameState(char: Char) {
        launch {
            val game = repository.gameState()

            if (!game.gameOn && !game.gameOver) {

                repository.verifyAnswerThenUpdateGameState(char)

                updateLettersUiModel(GameMoveForward.VERIFY_ANSWER, char)

                val state = repository.gameState()

                val gameState = gameStateAfterGameOnOrOver(state)

                _uiState.update {
                    state.toExternal(options = letters)
                        .copy(gameState = gameState)
                }
            }
        }
    }

    fun onIntent(event: UiDialogIntent) {
        when (event) {
            is UiDialogIntent.RefreshConnection -> refresh()
            is UiDialogIntent.RestartGame -> restartGame()
            is UiDialogIntent.NextWord -> nextWord()
            is UiDialogIntent.DisplayPerformance -> displayPerformance(event.display)
            is UiDialogIntent.DisplayReview -> displayReview(event.display)
            is  UiDialogIntent.StartNewGame -> prepareGame()
        }
    }

    private fun displayReview(display: Boolean) {
        _uiState.update {
            it.copy(
                displayReview = display,
            )
        }
    }

    private fun prepareGame() {
        launch {

            val gameState = if (hasConnection) {

                _uiState.update {
                    it.copy(
                        gameState = GameState.PREPARING,
                    )
                }

                repository.prepareGame()
                GameState.IDLE
            } else {
                GameState.NO_NETWORK
            }

            val state = repository.gameState().toExternal(options = letters)
            _uiState.update {
                state.copy(
                    gameState = gameState,
                )
            }
        }
    }

    private suspend fun gameStateAfterGameOnOrOver(state: Trial) =
        if (state.gameOn || state.gameOver) {

            reviewAnswer.addReviewAnswer(
                AnswerModel(
                    word = state.answer,
                    isCorrect = state.gameOn
                )
            )


            if (state.gameIsFinish) {
                GameState.ENDED
            } else {
                GameState.SHOW_RESPONSE
            }
        } else {
            GameState.IDLE
        }

    private fun updateLettersUiModel(status: GameMoveForward, char: Char? = null) {

        when (status) {

            GameMoveForward.RESET -> {
                letters = letters.map {
                    it.copy(isSelected = false, isEnabled = true)
                }.toMutableList()
            }

            GameMoveForward.VERIFY_ANSWER -> {
                letters = letters.map {
                    if (it.char == char) {
                        it.copy(isSelected = true, isEnabled = false)
                    } else {
                        it.copy()
                    }
                }.toMutableList()
            }

        }

    }

    private fun refresh() {
        launch {
            if (_uiState.value.gameIsFinish) {
                restartGame()
            } else {
                _uiState.update {
                    it.copy(gameState = GameState.PREPARING)
                }
                prepareGame()
            }
        }
    }

    private fun nextWord() {
        launch {
            repository.resetGame()
            updateLettersUiModel(GameMoveForward.RESET)
            _uiState.update {
                repository.gameState().toExternal(options = letters)
            }
        }
    }

    private fun restartGame() {
        launch {

            reviewAnswer.clear()

            if (hasConnection) {
                _uiState.update {
                    it.copy(
                        gameState = GameState.PREPARING,
                    )
                }
                withContext(Dispatchers.IO) {
                    repository.resetGame()
                }
                updateLettersUiModel(GameMoveForward.RESET)
                _uiState.update {
                    repository.gameState().toExternal(options = letters)
                }
            } else {
                _uiState.update {
                    HangmanGameUiState(
                        gameState = GameState.NO_NETWORK,
                        gameIsFinish = it.gameIsFinish,
                    )
                }
            }
        }
    }

    private fun displayPerformance(display: Boolean) {
        _uiState.update {
            it.copy(
                displaySeePerformanceButton = !display,
                gameState = GameState.DISPLAY_PERFORMANCE,
                review = reviewAnswer.reviewAnswers()
            )
        }
    }

    companion object {
        val Factory = initializer {
            HangmanGameViewModel(
                repository = HangmanGameImpl.getInstance(),
                reviewAnswer = ReviewAnswerImpl.getInstance()
            )
        }
    }
}

@Composable
fun <T> viewModel(factory: T): T = factory