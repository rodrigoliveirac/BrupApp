package com.rodcollab.brupapp.multiplayer.ui

import com.rodcollab.brupapp.multiplayer.GameRoom
import com.rodcollab.brupapp.multiplayer.GameRoomRepository
import com.rodcollab.brupapp.multiplayer.GameRoomRepositoryImpl
import com.rodcollab.brupapp.util.initializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MultiplayerUiState(
    val placeHolderText: String = "No rooms created",
    val rooms: List<GameRoom> = listOf(),
    val openFormDialog: Boolean = false
)

class MultiplayerViewModel(private val gameRoom: GameRoomRepository) :
    CoroutineScope by MainScope() {

    private val _uiState: MutableStateFlow<MultiplayerUiState> by lazy {
        MutableStateFlow(MultiplayerUiState())
    }
    val uiState = _uiState.asStateFlow()

    init {
        launch {
            _uiState.update {
                it.copy(rooms = gameRoom.getRoom())
            }
        }
    }

    fun createRoom(roomName: RoomFormField) {
        launch {
            gameRoom.createRoom(roomName.roomName, roomName.creatorName)
            _uiState.update {
                it.copy(rooms = gameRoom.getRoom())
            }
        }
    }

    companion object {
        val Factory = initializer {
            MultiplayerViewModel(GameRoomRepositoryImpl.get())
        }
    }
}
