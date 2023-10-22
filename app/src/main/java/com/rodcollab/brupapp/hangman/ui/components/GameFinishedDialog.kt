package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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

    if (uiState.gameOver) title = "You lost! :(" else if (uiState.gameOn) title = "Congrats! :)"
    if(uiState.chars.isEmpty()) title = "Preparing the game"

    if (uiState.gameOn || uiState.gameOver || uiState.chars.isEmpty()) {
        AlertDialog(
            title = {
                Box(Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = title)
                }
            },
            text = {
                Box(Modifier.fillMaxWidth()) {
                    if(uiState.chars.isEmpty()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "The word is '${uiState.answer}'"
                        )
                    }
                }
            },
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                if(uiState.chars.isNotEmpty()) {
                    Box(Modifier.fillMaxWidth()) {
                        Button(modifier = Modifier.align(Alignment.Center), onClick = {
                            resetGame()
                        }) {
                            Text(text = "Try another word")
                        }
                    }
                }
            }
        )
    }
}