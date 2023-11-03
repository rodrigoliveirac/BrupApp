package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TitleDialog(title: String) {
    Box(Modifier.fillMaxWidth()) {
        Text(modifier = Modifier.align(Alignment.Center), text = title)
    }
}