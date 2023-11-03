package com.rodcollab.brupapp.hangman.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.components.menu.GameMenuDialog
import com.rodcollab.brupapp.hangman.ui.components.game.SetupHangmanGameScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHost(gameViewModel: HangmanGameViewModel = viewModel(factory = HangmanGameViewModel.Factory)) {

    val uiState by gameViewModel.uiState.collectAsState()

    GameMenuDialog(
        uiState = uiState,
        onIntent = { intent -> gameViewModel.onIntent(intent) }
    )

    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier.shadow(6.dp), title = {
            Text(text = "GuessWord")
        })
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            SetupHangmanGameScreen(
                uiState = uiState,
                verifyAnswerThenUpdateGameState = { char ->
                    gameViewModel.verifyAnswerThenUpdateGameState(char)
                })
        }
    }
}


