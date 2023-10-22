package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterItem(letter: String, letterGuessed: Boolean) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()
    ) {
        val isLetter = alreadyUsed(letter.first())
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .graphicsLayer {
                    alpha = if (letterGuessed || !isLetter) 1f else 0f
                }, text = letter.uppercase(), fontSize = 16.sp, fontWeight = FontWeight.ExtraBold
        )
        if(isLetter) {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .width(12.dp)
                    .background(Color.LightGray)
            )
        }
    }
}

@Composable
fun alreadyUsed(c: Char) : Boolean = when (c) {
    in 'a'..'z', in 'A'..'Z' -> true
    else -> false
}