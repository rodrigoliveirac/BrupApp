package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.CharItem

@Composable
fun FilledWord(modifier: Modifier, chars: List<CharItem>) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        chars.forEach { char ->
            LetterItem(char)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}