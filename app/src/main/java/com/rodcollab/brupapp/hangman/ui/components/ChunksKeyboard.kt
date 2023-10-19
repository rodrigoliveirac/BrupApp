package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChunksKeyboard(
    modifier: Modifier,
    chunks: List<List<Char>>,
    letterTapped: Char,
    usedLetters: List<Char>,
    onTapped: (Char) -> Unit
) {
    chunks.forEach { chars ->
        Row(
            modifier = modifier,
        ) {
            chars.forEach { char ->
                CustomKeyboardButton(char, letterTapped, usedLetters, onTapped)
            }
        }
    }

}