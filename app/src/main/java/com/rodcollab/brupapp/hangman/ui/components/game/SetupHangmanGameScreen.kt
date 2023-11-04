package com.rodcollab.brupapp.hangman.ui.components.game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.HangmanGameUiState

@Composable
fun SetupHangmanGameScreen(uiState: HangmanGameUiState, verifyAnswerThenUpdateGameState: (Char) -> Unit) {

    val localConfig = LocalConfiguration.current

    if (localConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {

            Text(modifier = Modifier.padding(16.dp),text = uiState.tip)
            ScoreHeader(Modifier, score = uiState.score)

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledWord(
                    modifier = Modifier.filledWord(),
                    chars = uiState.chars,
                )

                Spacer(modifier = Modifier.height(16.dp))

                KeyBoard(onTapped = verifyAnswerThenUpdateGameState, letters = uiState.letterOptions)
            }
        }
    } else {
        Column(
            Modifier
                .sizeIn(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(modifier = Modifier.padding(24.dp).align(Alignment.CenterHorizontally),text = uiState.tip)
            ScoreHeader(Modifier.align(Alignment.Start), score = uiState.score)
            FilledWord(
                modifier = Modifier.filledWord(),
                chars = uiState.chars,
            )
            Spacer(modifier = Modifier.height(16.dp))
            KeyBoard(onTapped = verifyAnswerThenUpdateGameState, letters = uiState.letterOptions)
        }
    }
}

fun Modifier.filledWord() = this
    .padding(24.dp)
    .wrapContentSize(unbounded = true)