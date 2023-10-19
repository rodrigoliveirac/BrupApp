package com.rodcollab.brupapp.hangman.ui

import androidx.compose.runtime.Composable
import com.rodcollab.brupapp.hangman.domain.Trial
import com.rodcollab.brupapp.hangman.repository.HangmanGame
import com.rodcollab.brupapp.hangman.repository.HangmanGameImpl
import com.rodcollab.brupapp.util.initializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HangmanGameUiState(
    val chars: List<Char> = listOf(),
    val usedLetters: List<Char> = listOf(),
    val chances: Int = 0,
    val tries: Int = 0,
    val hits: Int = 0,
    val errors: Int = 0,
    val answer: String = "",
) {
    val win: Boolean = usedLetters.containsAll(chars)
    val lose: Boolean = chances == 0
    val finished: Boolean = win || lose
}

fun Trial.toExternal() =
    HangmanGameUiState(chars, usedLetters, chances, tries, hits, errors, answer)

abstract class SimpleViewModel()
class HangmanGameViewModel(private val repository: HangmanGame) : SimpleViewModel() {

    private val _uiState: MutableStateFlow<HangmanGameUiState> by lazy {
        MutableStateFlow(
            HangmanGameUiState()
        )
    }
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            repository.getTrialState().toExternal()
        }
    }

    fun verifyAnswerThenUpdateGameState(char: Char) {
        repository.verifyAnswerThenUpdateGameState(char)
        _uiState.update {
            repository.getTrialState().toExternal()
        }
    }

    fun resetGame() {
        repository.resetGame()
        _uiState.update {
            repository.getTrialState().toExternal()
        }
    }

    companion object {
        val Factory = initializer {
            HangmanGameViewModel(HangmanGameImpl.getInstance())
        }
    }
}

@Composable
fun <T> viewModel(factory: T) : T = factory