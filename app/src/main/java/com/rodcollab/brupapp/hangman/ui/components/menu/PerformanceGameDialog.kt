package com.rodcollab.brupapp.hangman.ui.components.menu

import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.repository.AnswerModel
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent
import com.rodcollab.brupapp.util.createScreenshot
import kotlinx.coroutines.launch

@Composable
fun PerformanceGameDialog(
    performanceValue: Float,
    performanceText: String,
    displayReview: Boolean,
    review: List<AnswerModel>,
    sharePerformance: (Uri) -> Unit,
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

        val view = LocalView.current
        val ctx = LocalContext.current
        val scope = rememberCoroutineScope()

        TitleDialog("Your performance was")
        Spacer(modifier = Modifier.size(16.dp))
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
        Spacer(modifier = Modifier.size(16.dp))
        ShowTheAnswersContent(onIntent, displayReview)
        Spacer(modifier = Modifier.size(8.dp))

        if (displayReview) {
            Answers(review)
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), onClick = {
            onIntent(UiDialogIntent.StartNewGame)
        }) {
            Text(text = "Start a new game")
        }

        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.End)
                .padding(bottom = 4.dp), onClick = {
                scope.launch { sharePerformance(createScreenshot(view, ctx)) }
            }, colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(color = Color.Gray, text = "Share")
            Spacer(modifier = Modifier.size(8.dp))
            Icon(tint = Color.Gray, imageVector = Icons.Default.Share, contentDescription = null)
        }
    }
}
@Composable
private fun ShowTheAnswersContent(
    onIntent: (UiDialogIntent) -> Unit,
    displayReview: Boolean
) {
    Row(modifier = Modifier.clickable { onIntent(UiDialogIntent.DisplayReview(!displayReview)) }) {
        Text(text = "Show the answers")
        Icon(
            imageVector = if (displayReview) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null
        )
    }
}

@Composable
private fun Answers(review: List<AnswerModel>) {
    review.map { model ->
        Row(modifier = Modifier.fillMaxWidth()) {
            AnswerItem(Modifier.weight(1f), model)
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
private fun AnswerItem(modifier: Modifier, model: AnswerModel) {
    Text(modifier = modifier, text = model.word)
    Icon(
        imageVector = if (model.isCorrect) Icons.Default.Check else Icons.Default.Clear,
        contentDescription = null,
        tint = if (model.isCorrect) Color.Green else Color.Red
    )
}

@Composable
@Preview
fun PerformanceGameDialogPreview() {
    val performance by remember { mutableStateOf(Animatable(initialValue = 0.0f)) }

    LaunchedEffect(Unit) {
        performance.animateTo(
            0.4f,
            TweenSpec(1000, delay = 1000, EaseInCirc)
        )
    }
    WidgetDialog {
        TitleDialog("Your performance was")
        Spacer(modifier = Modifier.size(16.dp))
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
                text = "40%"
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), onClick = {
            // onIntent(UiDialogIntent.StartNewGame)
        }) {
            Text(text = "Start a new game")
        }
        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.End)
                .padding(bottom = 4.dp), onClick = {
            }, colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Text(color = Color.Gray, text = "Share")
            Spacer(modifier = Modifier.size(8.dp))
            Icon(tint = Color.Gray, imageVector = Icons.Default.Share, contentDescription = null)
        }
    }
}