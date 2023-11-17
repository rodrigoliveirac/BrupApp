package com.rodcollab.brupapp.multiplayer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.rodcollab.brupapp.hangman.ui.viewModel

@Composable
fun MultiplayerHost(multiplayerViewModel: MultiplayerViewModel = viewModel(factory = MultiplayerViewModel.Factory)) {
    val uiState by multiplayerViewModel.uiState.collectAsState()
    MultiplayerScreen(
        multiplayerUiState = uiState,
        createRoom = { roomFormField ->  multiplayerViewModel.createRoom(roomFormField) })
}