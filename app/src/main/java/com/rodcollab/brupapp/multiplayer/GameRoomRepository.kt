package com.rodcollab.brupapp.multiplayer

interface GameRoomRepository {

    val userApplicationId: String?

    suspend fun createRoom(roomName: String, creatorName: String)
    suspend fun getRoom(): List<GameRoom>
}
