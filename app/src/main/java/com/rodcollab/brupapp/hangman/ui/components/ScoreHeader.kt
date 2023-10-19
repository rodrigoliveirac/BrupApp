package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T, K> ScoreHeader(modifier:Modifier,score: HashMap<T, K>) {
    Column(modifier = modifier.padding(16.dp)) {
        score.forEach { (text, value) ->
            Text(
                modifier = Modifier.align(Alignment.Start).padding(start = 16.dp),
                text = "$text$value"
            )
        }
    }
}