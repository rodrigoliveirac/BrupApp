package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilledWord(modifier: Modifier, chars: List<Char>, usedLetters: List<Char>) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        chars.forEach { char ->
            val letterGuessed = usedLetters.any { l -> char == l }
            LetterItem(char.toString(), letterGuessed)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}