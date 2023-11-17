package com.rodcollab.brupapp.multiplayer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiplayerScreen(multiplayerUiState: MultiplayerUiState, createRoom: (RoomFormField) -> Unit) {

    var openDialog by rememberSaveable { mutableStateOf(false) }


    if (openDialog) {
        MultiplayerFormRoomDialog(createRoom = {
            openDialog = !openDialog
            createRoom(it)
        })
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.shadow(6.dp),
                title = {
                    Text(text = "Multiplayer GuessWord")
                })
        }, floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { openDialog = !openDialog },
                icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                text = { Text(text = "Create a room") },
            )
        }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (multiplayerUiState.rooms.isEmpty()) {
                    Text(modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(), text = multiplayerUiState.placeHolderText)
                } else {
                    LazyColumn {
                        items(multiplayerUiState.rooms) { room ->
                            Column(Modifier.padding(16.dp)) {
                                Text(text = room.roomName)
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(text = room.creatorName)
                            }
                        }
                    }
                }
            }
        }
    }
}