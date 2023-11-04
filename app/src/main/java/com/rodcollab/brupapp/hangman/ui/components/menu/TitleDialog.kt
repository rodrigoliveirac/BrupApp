package com.rodcollab.brupapp.hangman.ui.components.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TitleDialog(title: String) {
    Box(Modifier.fillMaxWidth()) {
        Text(modifier = Modifier.align(Alignment.Center), text = title, fontSize = 24.sp)
    }
}
@Composable
fun DescriptionDialog(answer: String) {
    Box(Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.alpha(0.7f),text = "The word is ", fontSize = 16.sp)
            Text(text = answer, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}