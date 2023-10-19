package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rodcollab.brupapp.hangman.ui.HangmanGameUiState

@Composable
fun GameFinishedDialog(
    uiState: HangmanGameUiState,
    resetGame: () -> Unit
) {
    var title by remember { mutableStateOf("") }

    if (uiState.lose) title = "You lost! :(" else if (uiState.win) title = "Congrats! :)"
    if (uiState.finished) {
        AlertDialog(
            title = {
                Box(Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = title)
                }
            },
            text = {
                Box(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "The word is '${uiState.answer}'"
                    )
                }
            },
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                Box(Modifier.fillMaxWidth()) {
                    Button(modifier = Modifier.align(Alignment.Center), onClick = {
                        resetGame()
                    }) {
                        Text(text = "Try another word")
                    }
                }
            }
        )
    }
}