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
    chunks:Int,
    verifyAnswer: (Char) -> Unit
) {
    Chunks(
        modifier = modifier,
        letters = letters,
        chunks = chunks
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
    chunks: Int,
    content: @Composable RowScope.(List<LetterModel>) -> Unit
) {
    letters.chunked(chunks).forEach {
        Row(modifier = modifier) {
            content(it)
        }
    }
}