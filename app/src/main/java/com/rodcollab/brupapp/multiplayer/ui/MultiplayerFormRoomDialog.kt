package com.rodcollab.brupapp.multiplayer.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodcollab.brupapp.hangman.ui.components.menu.WidgetDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiplayerFormRoomDialog(createRoom: (RoomFormField) -> Unit) {

    var creatorName by rememberSaveable { mutableStateOf("") }
    var roomName by rememberSaveable { mutableStateOf("") }

    WidgetDialog {
        Text(text = "Create a Room")
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            label = { Text(text = "Creator Name") },
            value = creatorName,
            onValueChange = { currentCreatorName ->
                creatorName = currentCreatorName
            })
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            label = { Text(text = "Room Name") },
            value = roomName,
            onValueChange = { currentRoomName -> roomName = currentRoomName })
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = { createRoom(RoomFormField(creatorName = creatorName, roomName = roomName)) }) {
            Text(text = "Confirm")
        }
    }
}