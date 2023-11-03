package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.runtime.Composable
import com.rodcollab.brupapp.hangman.ui.HangmanGameUiState
import com.rodcollab.brupapp.hangman.ui.enums.GameState.DISPLAY_PERFORMANCE
import com.rodcollab.brupapp.hangman.ui.enums.GameState.ENDED
import com.rodcollab.brupapp.hangman.ui.enums.GameState.NO_NETWORK
import com.rodcollab.brupapp.hangman.ui.enums.GameState.PREPARING
import com.rodcollab.brupapp.hangman.ui.enums.GameState.SHOW_RESPONSE
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent

@Composable
fun GameMenuDialog(
    uiState: HangmanGameUiState,
    onIntent: (UiDialogIntent) -> Unit
) {

    when (uiState.gameState) {

        NO_NETWORK -> {
            NoNetworkDialog(onIntent = onIntent)
        }

        PREPARING -> {
            PrepareGameDialog()
        }

        SHOW_RESPONSE -> {
            ShowResponseTrialDialog(
                title = uiState.title,
                answer = uiState.answer,
                onIntent = onIntent
            )
        }

        ENDED -> {
            EndOfTheGameDialog(
                title = uiState.title,
                answer = uiState.answer,
                displayPerformanceButton = uiState.displaySeePerformanceButton,
                onIntent = onIntent
            )
        }

        DISPLAY_PERFORMANCE -> {
            PerformanceGameDialog(
                performanceValue = uiState.performance.first,
                performanceText = uiState.performance.second,
                onIntent
            )
        }

        else -> {}
    }
}