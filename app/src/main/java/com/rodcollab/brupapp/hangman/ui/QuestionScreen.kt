package com.rodcollab.brupapp.hangman.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.components.FilledWord
import com.rodcollab.brupapp.hangman.ui.components.KeyBoard
import com.rodcollab.brupapp.hangman.ui.components.ScoreHeader
import kotlinx.coroutines.delay

val alphabet = mutableListOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuestionScreen(gameViewModel: HangmanGameViewModel = HangmanGameViewModel.Factory) {


    val uiState by gameViewModel.uiState.collectAsState()

    var letterTapped by remember { mutableStateOf('-') }

    val score = hashMapOf<String, Any>(
        "Tries: " to uiState.tries,
        "Chances left: " to uiState.chances,
        "Hits: " to uiState.hits,
        "Wrongs: " to uiState.errors,
        "Used letters: " to uiState.usedLetters.toString()
    )

    var openDialogLostGame by rememberSaveable { mutableStateOf(false) }
    var openDialogWinGame by rememberSaveable { mutableStateOf(false) }

    var resetGame by remember { mutableStateOf(false) }

    if (uiState.chances == 0) {
        openDialogLostGame = true
    }

    if (uiState.usedLetters.containsAll(uiState.chars)) {
        LaunchedEffect(Unit) {
            delay(100)
            openDialogWinGame = true
        }
    }


    LaunchedEffect(letterTapped) {

        delay(2L)
        letterTapped = '-'

    }

    val localConfig = LocalConfiguration.current

    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier.shadow(6.dp), title = {
            Text(text = "Hangman")
        })
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (localConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    ScoreHeader(score = score)

                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FilledWord(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(24.dp)
                                .wrapContentSize(unbounded = true),
                            chars = uiState.chars,
                            usedLetters = uiState.usedLetters
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        KeyBoard(modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(), onTapped = { char ->
                            letterTapped = char
                            gameViewModel.verifyAnswerThenUpdateGameState(char)
                        }, usedLetters = uiState.usedLetters, letterTapped = letterTapped)
                    }
                }
            } else {
                Column(
                    Modifier
                        .align(Alignment.Center)
                        .sizeIn(),
                ) {

                    ScoreHeader(score = score)

                    FilledWord(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(24.dp)
                            .wrapContentSize(unbounded = true),
                        chars = uiState.chars,
                        usedLetters = uiState.usedLetters
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    KeyBoard(modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(), onTapped = { char ->
                        letterTapped = char
                        gameViewModel.verifyAnswerThenUpdateGameState(char)
                    }, usedLetters = uiState.usedLetters, letterTapped = letterTapped)
                }
            }
        }
    }


    if (openDialogLostGame) {
        AlertDialog(
            title = {
                Box(Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = "You lost! :(")
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
                        gameViewModel.resetGame()
                        resetGame = !resetGame
                        openDialogLostGame = false
                    }) {
                        Text(text = "Try another word")
                    }
                }
            }
        )
    }

    if (openDialogWinGame) {
        AlertDialog(
            title = {
                Box(Modifier.fillMaxWidth()) {
                    Text(modifier = Modifier.align(Alignment.Center), text = "Congrats! :)")
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
                        gameViewModel.resetGame()
                        resetGame = !resetGame
                        openDialogWinGame = false
                    }) {
                        Text(text = "Try another word")
                    }
                }
            }
        )
    }
}