package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent

@Composable
fun MenuDialog(
    onIntent: (UiDialogIntent) -> Unit,
    toMultiplayerScreen: () -> Unit,
) {

    WidgetDialog {
        TitleDialog(title = "MENU")
        Spacer(modifier = Modifier.size(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth(), onClick = {
            onIntent(UiDialogIntent.StartNewGame)
        }) {
            Text(text = "Start a new game")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(), onClick = toMultiplayerScreen
        ) {
            Text(text = "Multiplayer")
        }
    }
}

@Preview
@Composable
fun MenuDialogPreview() {

    WidgetDialog {
        TitleDialog(title = "MENU")
        Spacer(modifier = Modifier.size(16.dp))
        Button(modifier = Modifier
            .fillMaxWidth(), onClick = {
            // onIntent(UiDialogIntent.StartNewGame)
        }) {
            Text(text = "Start a new game")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(modifier = Modifier
            .fillMaxWidth(), onClick = {
            // onIntent(UiDialogIntent.StartNewGame)
        }) {
            Text(text = "Multiplayer")
        }
    }
}