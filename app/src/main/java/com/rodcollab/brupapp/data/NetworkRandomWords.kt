package com.rodcollab.brupapp.data

import com.rodcollab.brupapp.BuildConfig
import com.rodcollab.brupapp.di.WordnikConnection
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET

interface NetworkRandomWords {
    suspend fun randomWords(): List<WordItem>
}

class NetworkRandomWordsImpl : NetworkRandomWords {

    override suspend fun randomWords(): List<WordItem> {
        if (service.randomWords().isSuccessful) {
            return service.randomWords().body()!!
        }
        return emptyList()
    }

    companion object : WordnikConnection() {

        private var instance: NetworkRandomWords? = null

        fun getInstance(): NetworkRandomWords {
            if (instance == null) {
                instance = NetworkRandomWordsImpl()
            }
            return instance!!
        }
    }
}

interface WordnikService {

    @GET("randomWords?limit=7&api_key=${BuildConfig.API_KEY}")
    suspend fun randomWords(): Response<ArrayList<WordItem>>

}

@Serializable
data class WordItem(
    val id: Int,
    val word: String
)