package com.rodcollab.brupapp.hangman.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodcollab.brupapp.hangman.repository.HangmanGame
import kotlinx.coroutines.delay

val alphabet = mutableListOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuestionScreen(game: HangmanGame) {


    var uiState by remember { mutableStateOf(game.getTrialState()) }

    var letterTapped by remember { mutableStateOf('-') }

    val score = hashMapOf(
        "Tries: " to uiState.tries,
        "Chances left: " to uiState.chances,
        "Hits: " to uiState.hits,
        "Wrongs: " to uiState.errors,
        "Used letters: " to uiState.usedLetters.toString()
    )

    var openDialogLostGame by rememberSaveable { mutableStateOf(false) }
    var openDialogWinGame by rememberSaveable { mutableStateOf(false) }

    var resetGame by remember { mutableStateOf(false) }

    val chars by remember { mutableStateOf(game.getAnswerListChar()) }

    if (game.getTrialState().chances == 0) {
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

        uiState = game.getTrialState()
    }

    LaunchedEffect(resetGame) {
        uiState = game.getTrialState()
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
                    Column(modifier = Modifier.padding(16.dp)) {
                        score.forEach { (text, value) ->
                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = text + value.toString()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(24.dp)
                                .wrapContentSize(unbounded = true),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            chars.forEach { char ->

                                val letterGuessed = game.guessedLetters().any { l -> char == l }
                                LetterItem(char.toString(), letterGuessed)
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        alphabet.chunked(10).forEach { chars ->

                            Row(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                chars.forEach { char ->
                                    Card(
                                        colors = CardDefaults.cardColors(Color(255, 255, 255, 255)),
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(
                                            2.dp, Color(
                                                250,
                                                128,
                                                46
                                            )
                                        ),
                                        modifier = Modifier
                                            .graphicsLayer {
                                                scaleX =
                                                    if (char == letterTapped) 1.2f else 1.0f
                                                scaleY =
                                                    if (char == letterTapped) 1.2f else 1.0f
                                            }
                                            .pointerInput(Unit) {
                                                detectTapGestures(
                                                    onPress = {

                                                        val letterGuessed = game
                                                            .guessedLetters()
                                                            .any { l -> char == l }

                                                        if (!letterGuessed) {
                                                            letterTapped = char

                                                            game.verifyAnswerThenUpdateGameState(
                                                                char
                                                            )
                                                        }

                                                    }
                                                )
                                            }
                                            .padding(4.dp),
                                        elevation = CardDefaults.cardElevation(4.dp)
                                    ) {
                                        Text(
                                            text = char.toString(),
                                            maxLines = 1,
                                            modifier = Modifier
                                                .padding(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Column(
                    Modifier
                        .align(Alignment.Center)
                        .sizeIn(),
                ) {

                    score.forEach { (text, value) ->
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = text + value.toString()
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(24.dp)
                            .wrapContentSize(unbounded = true),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        chars.forEach { char ->

                            val letterGuessed = game.guessedLetters().any { l -> char == l }
                            LetterItem(char.toString(), letterGuessed)
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    alphabet.chunked(8).forEach { chars ->

                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                        ) {
                            chars.forEach { char ->
                                Card(
                                    colors = CardDefaults.cardColors(Color(255, 255, 255, 255)),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(
                                        2.dp, Color(
                                            250,
                                            128,
                                            46
                                        )
                                    ),
                                    modifier = Modifier
                                        .graphicsLayer {
                                            scaleX =
                                                if (char == letterTapped) 1.2f else 1.0f
                                            scaleY =
                                                if (char == letterTapped) 1.2f else 1.0f
                                        }
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onPress = {

                                                    val letterGuessed = game
                                                        .guessedLetters()
                                                        .any { l -> char == l }

                                                    if (!letterGuessed) {
                                                        letterTapped = char

                                                        game.verifyAnswerThenUpdateGameState(
                                                            char
                                                        )
                                                    }

                                                }
                                            )
                                        }
                                        .padding(4.dp),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Text(
                                        text = char.toString(),
                                        maxLines = 1,
                                        modifier = Modifier
                                            .padding(12.dp)
                                    )
                                }
                            }
                        }
                    }
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
                        game.resetGame()
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
                        game.resetGame()
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

@Composable
fun LetterItem(letter: String, letterGuessed: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .graphicsLayer {
                    alpha = if (letterGuessed) 1f else 0f
                }, text = letter.uppercase(), fontSize = 16.sp, fontWeight = FontWeight.ExtraBold
        )
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .width(12.dp)
                .background(Color.LightGray)
        )
    }
}