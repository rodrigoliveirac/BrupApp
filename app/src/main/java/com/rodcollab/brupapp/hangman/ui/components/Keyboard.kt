package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rodcollab.brupapp.hangman.ui.alphabet

@Composable
fun KeyBoard(
    modifier: Modifier,
    onTapped: (Char) -> Unit,
    usedLetters: List<Char>,
    letterTapped: Char,
) {

    val chunks = alphabet.chunked(8)

    ChunksKeyboard(modifier = modifier, chunks, letterTapped, usedLetters, onTapped)
}