package com.rodcollab.brupapp.multiplayer

data class GameRoom(
    val userCreatorId: String,
    val roomName: String,
    val creatorName: String
) : NetworkData()

abstract class NetworkData