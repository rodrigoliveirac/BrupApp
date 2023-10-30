package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.HangmanGameUiState

@Composable
fun GameFinishedDialog(
    uiState: HangmanGameUiState,
    resetGame: (String) -> Unit,
    refreshConnection: () -> Unit
) {

    //TODO("need to review. ui logic should not be complex")

    var title by remember { mutableStateOf("") }
    var displayPerformance by remember { mutableStateOf(false) }

    if (uiState.gameOver) title = "You lost! :(" else if (uiState.gameOn) title = "Congrats! :)"
    if (uiState.isLoading) title = "Preparing the game"
    if (uiState.displayPerformance && displayPerformance) title = "Your Performance was"
    if (!uiState.networkStatus && !uiState.isLoading) title = "No internet :("

    var perfomance by remember { mutableStateOf(Animatable(initialValue = 0.0f)) }

    LaunchedEffect(displayPerformance) {
        if (uiState.gameIsFinish) {
            perfomance.animateTo(
                uiState.performance.first,
                TweenSpec(1000, delay = 1000, EaseInCirc)
            )
        }
    }

    if (uiState.gameOn || uiState.gameOver || uiState.isLoading || !uiState.networkStatus) {
        AlertDialog(
            title = {
                Box(Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = title)
                }
            },
            text = {
                Box(Modifier.fillMaxWidth()) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else if (uiState.displayPerformance && displayPerformance) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .align(
                                            Alignment.Center
                                        ), progress = perfomance.value, strokeWidth = 2.dp
                                )
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = uiState.performance.second
                                )
                            }
                        }
                    } else if (!uiState.networkStatus && !uiState.refreshDialog) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "sorry :("
                        )
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
                if (!uiState.isLoading) {
                    Box(Modifier.fillMaxWidth()) {
                        if (uiState.gameIsFinish) {
                            if(uiState.refreshDialog) {
                                Button(
                                    modifier = Modifier.align(Alignment.Center),
                                    onClick = refreshConnection
                                ) {
                                    Text(text = "Refresh")
                                }
                            } else {
                                Column() {
                                    Button(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 4.dp), onClick = {
                                        perfomance = Animatable(initialValue = 0.0f)
                                        resetGame("RESTART")
                                        displayPerformance = !displayPerformance
                                    }) {
                                        Text(text = "Start a new game")
                                    }
                                    if (uiState.displayPerformance && !displayPerformance) {
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            onClick = { displayPerformance = !displayPerformance }
                                        ) {
                                            Text(text = "See perfomance")
                                        }
                                    }
                                }
                            }
                        } else if (!uiState.networkStatus) {
                            Button(
                                modifier = Modifier.align(Alignment.Center),
                                onClick = refreshConnection
                            ) {
                                Text(text = "Refresh")
                            }
                        } else {
                            Button(
                                modifier = Modifier.align(Alignment.Center),
                                onClick = { resetGame("NEXT") }
                            ) {
                                Text(text = "Next word")
                            }
                        }
                    }
                }
            }
        )
    }
}


@Composable
@Preview
fun GameFinishedDialogPreview() {

    var gameOver by remember { mutableStateOf(true) }
    var gameOn by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var gameIsFinish by remember { mutableStateOf(true) }

    var title by remember { mutableStateOf("") }
    var displayPerformance by rememberSaveable { mutableStateOf(false) }


    if (gameOver) title = "You lost! :(" else if (gameOn) title = "Congrats! :)"
    if (isLoading) title = "Preparing the game"
    if (displayPerformance) title = "Your Performance was"
    var perfomance by remember { mutableStateOf(Animatable(initialValue = 0.0f)) }


    LaunchedEffect(Unit) {
        perfomance.animateTo(1.0f, TweenSpec(2000, delay = 1500, EaseInCirc))
    }


    if (gameOn || gameOver || isLoading) {
        AlertDialog(
            title = {
                Box(Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = title)
                }
            },
            text = {
                Box(Modifier.fillMaxWidth()) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else if (displayPerformance) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .align(
                                            Alignment.Center
                                        ), progress = perfomance.value, strokeWidth = 2.dp
                                )
                                Text(modifier = Modifier.align(Alignment.Center), text = "50%")
                            }
                        }
                    } else {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "The word is '${"word"}'"
                        )
                    }
                }
            },
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                if (!isLoading) {
                    Box(Modifier.fillMaxWidth()) {
                        if (gameIsFinish) {
                            Column() {
                                Button(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp), onClick = {

                                }) {
                                    Text(text = "Start a new game")
                                }
                                if (!displayPerformance) {
                                    Button(modifier = Modifier
                                        .fillMaxWidth(), onClick = {
                                        displayPerformance = true
                                    }) {
                                        Text(text = "See perfomance")
                                    }
                                }
                            }
                        } else {
                            Button(modifier = Modifier.align(Alignment.Center), onClick = {

                            }) {
                                Text(text = "Try another word")
                            }
                        }

                    }
                }
            }
        )
    }
}