package com.rodcollab.brupapp.hangman.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun CustomKeyboardButton(
    char: Char,
    letterTapped: Char,
    usedLetters: List<Char>,
    onTapped: (Char) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(Color(255, 255, 255, 255)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            2.dp, Color(
                250,
                128,
                46
            )
        ),
        modifier = Modifier
            .graphicsLayer {
                scaleX =
                    if (char == letterTapped) 1.2f else 1.0f
                scaleY =
                    if (char == letterTapped) 1.2f else 1.0f
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {

                        val letterGuessed = usedLetters
                            .any { l -> char == l }

                        if (!letterGuessed) {
                            onTapped(char)
                        }

                    }
                )
            }
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = char.toString(),
            maxLines = 1,
            modifier = Modifier
                .padding(12.dp)
        )
    }
}