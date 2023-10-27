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
import com.rodcollab.brupapp.hangman.ui.CharItem

@Composable
fun LetterItem(item: CharItem) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .graphicsLayer {
                    alpha =  if(item.guessed || !item.isLetter) 1.0f else 0.0f
                }, text = item.char, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold
        )
        if (item.isLetter) {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .width(12.dp)
                    .background(Color.LightGray)
            )
        }
    }
}