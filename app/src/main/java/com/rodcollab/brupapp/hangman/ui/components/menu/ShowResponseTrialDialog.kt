package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent

@Composable
fun ShowResponseTrialDialog(title: String, answer: String, onIntent: (UiDialogIntent) -> Unit) {
    WidgetDialog {
        TitleDialog(title = title)
        Spacer(modifier = Modifier.size(8.dp))
        DescriptionDialog(answer = answer)
        Spacer(modifier = Modifier.size(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), onClick = {
            onIntent(UiDialogIntent.NextWord)
        }) {
            Text(text = "Next word")
        }
    }
}

@Composable
@Preview
fun FinishedTrialDialogPreview(
    title: String = "You lost! :(",
    description: String = "the word was"
) {
    WidgetDialog {
        TitleDialog(title = title)
        Spacer(modifier = Modifier.size(8.dp))
        DescriptionDialog(answer = description)
        Spacer(modifier = Modifier.size(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp), onClick = {
//                perfomance = Animatable(initialValue = 0.0f)
//                onIntent(UiDialogIntent.StartNewGame)
        }) {
            Text(text = "Next word")
        }
    }
}