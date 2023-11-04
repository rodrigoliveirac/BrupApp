package com.rodcollab.brupapp.hangman.ui.components.game

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rodcollab.brupapp.hangman.ui.model.LetterModel

@Composable
fun ChunksKeyboard(
    modifier: Modifier,
    letters: List<LetterModel>,
    verifyAnswer: (Char) -> Unit
) {
    Chunks(
        modifier = modifier,
        letters = letters
    ) { chars ->
        chars.forEach { letter ->
            CustomKeyboardButton(
                letter,
                verifyAnswer
            )
        }
    }
}

@Composable
fun Chunks(
    modifier: Modifier,
    letters: List<LetterModel>,
    content: @Composable RowScope.(List<LetterModel>) -> Unit
) {
    letters.chunked(8).forEach {
        Row(modifier = modifier) {
            content(it)
        }
    }
}