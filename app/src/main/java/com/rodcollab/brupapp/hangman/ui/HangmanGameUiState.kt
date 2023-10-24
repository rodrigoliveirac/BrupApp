package com.rodcollab.brupapp.hangman.ui

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
    val letterOptions: List<LetterModel> = listOf(),
    val tip: String = ""
)