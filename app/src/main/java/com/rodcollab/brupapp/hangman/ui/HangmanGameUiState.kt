package com.rodcollab.brupapp.hangman.ui

import com.rodcollab.brupapp.hangman.ui.enums.GameState
import com.rodcollab.brupapp.hangman.ui.model.CharItem
import com.rodcollab.brupapp.hangman.ui.model.LetterModel

data class HangmanGameUiState(
    val score: HashMap<String, Any> = hashMapOf(),
    val gameOn: Boolean = false,
    val gameOver: Boolean = false,
    val chars: List<CharItem> = listOf(),
    val answer: String = "",
    val letterOptions: List<LetterModel> = listOf(),
    val tip: String = "",
    val gameIsFinish: Boolean = false,
    val newGame: Boolean = false,
    val performance: Pair<Float, String> = Pair(first = 0.0f, second = "0%"),
    val displayPerformance: Boolean = false,
    val networkStatus: Boolean = true,
    val refreshDialog: Boolean = false,
    val displaySeePerformanceButton: Boolean = false,
    val gameState: GameState = GameState.IDLE,
) {

    @get:JvmName(name = "title")
    val title: String by lazy { getTitle() }
    private fun getTitle(): String {

        var title = "Preparing the game"

        if (gameOver || gameOn) {
            title = if (gameOn) "Congrats! :)" else "You lost! :("
        }

        return title
    }

}