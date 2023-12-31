package com.rodcollab.brupapp.hangman.ui.components.game

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.model.LetterModel

@Composable
fun KeyBoard(
    modifier: Modifier = Modifier,
    onTapped: (Char) -> Unit,
    letters: List<LetterModel>,
    chunks:Int,
) {
    ChunksKeyboard(modifier = modifier.padding(start = 16.dp).fillMaxWidth(), letters,chunks, onTapped)
}