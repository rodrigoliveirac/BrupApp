package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.intent.UiDialogIntent

@Composable
fun NoNetworkDialog(onIntent: (UiDialogIntent) -> Unit) {
    WidgetDialog {
        TitleDialog(title = "No internet :(")
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = { onIntent(UiDialogIntent.RefreshConnection) }
        ) {
            Text(text = "Refresh")
        }
    }
}