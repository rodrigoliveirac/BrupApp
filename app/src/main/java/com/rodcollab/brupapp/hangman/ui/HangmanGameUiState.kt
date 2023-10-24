package com.rodcollab.brupapp.hangman.ui

data class HangmanGameUiState(
    val isLoading: Boolean = false,
    val score: HashMap<String, Any> = hashMapOf(),
    val gameOn: Boolean = false,
    val gameOver: Boolean = false,
    val chars: List<CharItem> = listOf(),
    val answer: String = "",
    val letterOptions: List<LetterModel> = listOf(),
    val tip: String = ""
)