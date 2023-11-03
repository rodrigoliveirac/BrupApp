package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent

@Composable
fun NoNetworkDialog(onIntent: (UiDialogIntent) -> Unit) {
    WidgetDialog {
        TitleDialog(title = "No internet :(")
        Button(
            onClick = { onIntent(UiDialogIntent.RefreshConnection) }
        ) {
            Text(text = "Refresh")
        }
    }
}