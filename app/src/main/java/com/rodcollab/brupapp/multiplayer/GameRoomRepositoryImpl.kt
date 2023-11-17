package com.rodcollab.brupapp.multiplayer

import com.rodcollab.brupapp.app.SharedPreferencesHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class GameRoomRepositoryImpl(
    private val sharedPrefs: SharedPreferencesHandler,
    private val cloud: NetworkDataSource<NetworkData>
) : GameRoomRepository {

    override val userApplicationId: String? by lazy {
        sharedPrefs.getString("userApplicationId")
    }

    override suspend fun createRoom(roomName: String, creatorName: String) {

        if (userApplicationId == null) {
            val uuid = withContext(Dispatchers.IO) { UUID.randomUUID().toString() }
            sharedPrefs.edit("userApplicationId", uuid)
        }

        val gameRoom = GameRoom(
            userCreatorId = userApplicationId.toString(),
            roomName = roomName,
            creatorName = creatorName
        )

        cloud.createDocument(
            document = userApplicationId.toString(),
            collection =  GAME_ROOM_COLLECTION,
            data = gameRoom
        )

    }

    override suspend fun getRoom(): List<GameRoom> {
        return withContext(Dispatchers.IO) {
            cloud.loadDocuments(
                GAME_ROOM_COLLECTION,
                USER_CREATOR_ID_FIELD,
                userApplicationId.toString()
            ).map {
                GameRoom(
                    userCreatorId = it.getString("userCreatorId")!!.toString(),
                    roomName = it.getString("roomName")!!.toString(),
                    creatorName = it.getString("creatorName")!!.toString()
                )
            }
        }
    }

    companion object {
        private const val GAME_ROOM_COLLECTION = "GAME_ROOM_COLLECTION"
        private const val USER_CREATOR_ID_FIELD = "userCreatorId"

        private var instance: GameRoomRepositoryImpl? = null

        fun getInstance(
            sharedPreferences: SharedPreferencesHandler,
            firebaseFirestore: NetworkDataSource<NetworkData>
        ): GameRoomRepository {
            if (instance == null) {
                instance = GameRoomRepositoryImpl(sharedPreferences, firebaseFirestore)
            }
            return instance!!
        }

        fun get(
        ): GameRoomRepository {
            return instance!!
        }

    }
}