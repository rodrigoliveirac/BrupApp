package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PrepareGameDialog() {
    WidgetDialog {
        TitleDialog("Preparing the game")
        Spacer(modifier = Modifier.size(16.dp))
        CircularProgressIndicator(strokeWidth = 2.dp)
    }
}

@Composable
@Preview
fun PrepareGameDialogPreview() {
    WidgetDialog {
        TitleDialog("Preparing the game")
        Spacer(modifier = Modifier.size(16.dp))
        CircularProgressIndicator(strokeWidth = 2.dp)
    }
}