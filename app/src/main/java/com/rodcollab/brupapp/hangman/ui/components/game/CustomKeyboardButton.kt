package com.rodcollab.brupapp.hangman.ui.components.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.model.LetterModel

@Composable
fun CustomKeyboardButton(
    letterModel: LetterModel,
    verifyAnswer: (Char) -> Unit
) {
    val elevationCard = elevationCard(letterModel.isSelected)
    var currentLetterTapped by remember { mutableStateOf('-') }
    LaunchedEffect(currentLetterTapped) { currentLetterTapped = '-' }

    Card(
        colors = CardDefaults.cardColors(Color(255, 255, 255, 255)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color(250, 128, 46)),
        modifier = Modifier
            .graphicsLayer {
                scaleX = if (currentLetterTapped == letterModel.char) 1.2f else 1.0f
                scaleY = if (currentLetterTapped == letterModel.char) 1.2f else 1.0f
                alpha = if (currentLetterTapped != letterModel.char && letterModel.isSelected) 0.5f else 1.0f
            }
            .pointerInput(currentLetterTapped, letterModel) {
                detectTapGestures(
                    onPress = {
                        if (letterModel.isEnabled) {
                            currentLetterTapped = letterModel.char
                            verifyAnswer(letterModel.char)
                        }
                    }
                )
            }
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(elevationCard)
    ) {
        Text(
            text = letterModel.char.toString(),
            maxLines = 1,
            modifier = Modifier
                .padding(12.dp)
        )
    }
}

@Composable
fun elevationCard(isAlreadyUsed: Boolean): Dp {
    return if (isAlreadyUsed) 0.dp else 4.dp
}