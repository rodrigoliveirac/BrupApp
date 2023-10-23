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

data class CharItem(
    val char: String,
    val isLetter: Boolean,
    val guessed: Boolean,
)

data class HangmanGameUiState(
    val score: HashMap<String, Any> = hashMapOf(),
    val isLoading: Boolean = false,
    val gameOn: Boolean = false,
    val gameOver: Boolean = false,
    val chars: List<CharItem> = listOf(),
    val usedLetters: List<Char> = listOf(),
    val chances: Int = 0,
    val tries: Int = 0,
    val hits: Int = 0,
    val errors: Int = 0,
    val answer: String = "",
    val letterOptions: List<LetterModel> = listOf()
)

data class LetterModel(
    val char: Char,
    val isEnabled: Boolean,
    val isSelected: Boolean
)

fun Trial.toExternal(options: List<LetterModel>) =
    HangmanGameUiState(
        score = hashMapOf(
            "Tries: " to tries,
            "Chances left: " to chances,
            "Hits: " to hits,
            "Wrongs: " to errors,
            "Used letters: " to usedLetters.toString()
        ),
        isLoading = false,
        gameOn,
        gameOver,
        chars.toCharItem(usedLetters),
        usedLetters,
        chances,
        tries,
        hits,
        errors,
        answer,
        letterOptions = options
    )

private fun isLetter(c: Char): Boolean = when (c) {
    in 'a'..'z', in 'A'..'Z' -> true
    else -> false
}

fun List<Char>.toCharItem(usedLetters: List<Char>) = map { char ->
    CharItem(
        char = char.uppercase(),
        isLetter = isLetter(char),
        guessed = usedLetters.any { it == char }
    )
}

class HangmanGameViewModel(private val repository: HangmanGame) : CoroutineScope by MainScope() {

    private val _uiState: MutableStateFlow<HangmanGameUiState> by lazy {
        MutableStateFlow(
            HangmanGameUiState()
        )
    }
    val uiState = _uiState.asStateFlow()

    private var letters = alphabet()

    private fun alphabet() = alphabet.map { char ->
        LetterModel(
            char = char,
            isEnabled = true,
            isSelected = false
        )
    }.toMutableList()


    init {
        launch {
            _uiState.update {
                repository.prepareGame().toExternal(options = letters)
            }
        }
    }

    fun verifyAnswerThenUpdateGameState(char: Char) {

        repository.verifyAnswerThenUpdateGameState(char)

        val updatedLetters = updateLettersUiModel(char)

        _uiState.update {
            repository.gameState().toExternal(options = updatedLetters)
        }
    }

    private fun updateLettersUiModel(char: Char): List<LetterModel> {
        letters = letters.map {
            if (it.char == char) {
                it.copy(isSelected = true, isEnabled = false)
            } else {
                it.copy()
            }
        }.toMutableList()
        return letters
    }

    fun resetGame() {
        repository.resetGame()
        _uiState.update {
            repository.gameState().toExternal(options = it.letterOptions)
        }
    }

    companion object {
        val Factory = initializer {
            HangmanGameViewModel(HangmanGameImpl.getInstance())
        }
    }
}

@Composable
fun <T> viewModel(factory: T): T = factory