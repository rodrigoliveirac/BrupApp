package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent

@Composable
fun PerformanceGameDialog(
    performanceValue: Float,
    performanceText: String,
    onIntent: (UiDialogIntent) -> Unit
) {
    val performance by remember { mutableStateOf(Animatable(initialValue = 0.0f)) }

    LaunchedEffect(Unit) {
        performance.animateTo(
            performanceValue,
            TweenSpec(800, delay = 500, EaseInCirc)
        )
    }

    WidgetDialog {
        TitleDialog("Your performance was")
        Spacer(modifier = Modifier.size(8.dp))
        Box {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(70.dp)
                    .align(
                        Alignment.Center
                    ), progress = performance.value, strokeWidth = 2.dp
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = performanceText
            )
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), onClick = {
            onIntent(UiDialogIntent.StartNewGame)
        }) {
            Text(text = "Start a new game")
        }
    }
}

@Composable
@Preview
fun PerformanceGameDialogPreview() {
    val perfomance by remember { mutableStateOf(Animatable(initialValue = 0.0f)) }

    LaunchedEffect(Unit) {
        perfomance.animateTo(
            0.4f,
            TweenSpec(1000, delay = 1000, EaseInCirc)
        )
    }
    WidgetDialog {
        TitleDialog("Your performance was")
        Spacer(modifier = Modifier.size(8.dp))
        Box {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(70.dp)
                    .align(
                        Alignment.Center
                    ), progress = perfomance.value, strokeWidth = 2.dp
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "40%"
            )
        }
    }
}