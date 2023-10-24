package com.rodcollab.brupapp.hangman.ui

import androidx.compose.runtime.Composable
import com.rodcollab.brupapp.hangman.domain.Trial
import com.rodcollab.brupapp.hangman.repository.HangmanGame
import com.rodcollab.brupapp.hangman.repository.HangmanGameImpl
import com.rodcollab.brupapp.util.initializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

fun Trial.toExternal(options: List<LetterModel>) =
    HangmanGameUiState(
        isLoading = false,
        score = hashMapOf(
            "Tries: " to tries,
            "Chances left: " to chances,
            "Hits: " to hits,
            "Wrongs: " to errors,
            "Used letters: " to usedLetters.toString()
        ),
        gameOn,
        gameOver,
        chars.toCharItem(usedLetters),
        answer,
        letterOptions = options,
        tip
    )

class HangmanGameViewModel(private val repository: HangmanGame) : CoroutineScope by MainScope() {

    private val _uiState: MutableStateFlow<HangmanGameUiState> by lazy {
        MutableStateFlow(HangmanGameUiState(isLoading = true))
    }
    val uiState = _uiState.asStateFlow()

    private var letters = alphabet.map { char ->
        LetterModel(
            char = char,
            isEnabled = true,
            isSelected = false
        )
    }.toMutableList()

    init {
        launch {
            val state = repository.prepareGame().toExternal(options = letters)
            _uiState.update { state }
        }
    }

    fun verifyAnswerThenUpdateGameState(char: Char) {

        repository.verifyAnswerThenUpdateGameState(char)

        updateLettersUiModel(GameStatus.VERIFY_ANSWER, char)

        _uiState.update {
            repository.gameState().toExternal(options = letters)
        }
    }

    fun resetGame() {
        repository.resetGame()
        updateLettersUiModel(GameStatus.RESET)
        _uiState.update {
            repository.gameState().toExternal(options = letters)
        }
    }

    private fun updateLettersUiModel(status: GameStatus, char: Char? = null) {

        when (status) {

            GameStatus.RESET -> {
                letters = letters.map {
                    it.copy(isSelected = false, isEnabled = true)
                }.toMutableList()
            }

            GameStatus.VERIFY_ANSWER -> {
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


    companion object {
        val Factory = initializer {
            HangmanGameViewModel(HangmanGameImpl.getInstance())
        }
    }
}

enum class GameStatus {
    RESET,
    VERIFY_ANSWER
}

@Composable
fun <T> viewModel(factory: T): T = factory