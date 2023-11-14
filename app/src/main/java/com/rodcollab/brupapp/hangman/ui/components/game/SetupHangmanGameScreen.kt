package com.rodcollab.brupapp.hangman.ui.components.game

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.HangmanGameUiState

@Composable
fun SetupHangmanGameScreen(
    uiState: HangmanGameUiState,
    verifyAnswerThenUpdateGameState: (Char) -> Unit
) {

    val localConfig = LocalConfiguration.current

    if (localConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        val scrollState = rememberScrollState()
        Column(modifier = Modifier.padding(8.dp).fillMaxSize().verticalScroll(state = scrollState)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                ScoreHeader(Modifier, score = uiState.score)

                Column(
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = uiState.tip)

                    FilledWord(
                        modifier = Modifier.filledWord(),
                        chars = uiState.chars,
                    )
                }
            }
            KeyBoard(
                onTapped = verifyAnswerThenUpdateGameState,
                letters = uiState.letterOptions,
                chunks = 16,
            )
        }
    } else {
        Column(
            Modifier
                .sizeIn(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally),
                text = uiState.tip,
                overflow = TextOverflow.Ellipsis
            )
            ScoreHeader(Modifier.align(Alignment.Start), score = uiState.score)
            FilledWord(
                modifier = Modifier.filledWord(),
                chars = uiState.chars,
            )
            Spacer(modifier = Modifier.height(16.dp))
            KeyBoard(onTapped = verifyAnswerThenUpdateGameState, letters = uiState.letterOptions, chunks = 8)
        }
    }
}

fun Modifier.filledWord() = this
    .padding(24.dp)
    .wrapContentSize(unbounded = true)