package com.rodcollab.brupapp.data

import android.util.Log
import com.rodcollab.brupapp.BuildConfig
import com.rodcollab.brupapp.di.WordnikConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkRandomWords {
    suspend fun randomWords(): List<WordItem>
    suspend fun definition(word: String): List<Definition>
}

class NetworkRandomWordsImpl : NetworkRandomWords {

    override suspend fun randomWords(): List<WordItem> {
        val response = withContext(Dispatchers.IO) { async { service.randomWords() }.await() }
        var randomWords = listOf<WordItem>()
        if (response.isSuccessful) {
            randomWords = service.randomWords().body()!!
        }
        return randomWords
    }

    override suspend fun definition(word: String): List<Definition> {
        val response = withContext(Dispatchers.IO) { async { service.definitions(word) }.await() }
        var definitions = listOf<Definition>()
        if (response.isSuccessful) {
            try {
                definitions = response.body()!!.map { jsonObject ->
                    val text = jsonObject["text"].toString()
                    Log.d("DEFINITIONS_JSON_OBJECT", text)
                    Definition(text = text)
                }
            } catch (e: Exception) {
                Log.d("DEFINITIONS_CATCH", e.message.toString())
                Definition(text = "")
            }
        }
        return definitions
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

    @GET("words.json/randomWords?limit=5&api_key=${BuildConfig.API_KEY}")
    suspend fun randomWords(): Response<ArrayList<WordItem>>

    @GET("word.json/{word}/definitions")
    suspend fun definitions(
        @Path("word") word: String,
        @Query("limit") limit: Int = 1,
        @Query("includeRelated") includeRelated: Boolean = false,
        @Query("useCanonical") useCanonical: Boolean = false,
        @Query("includeTags") includeTags: Boolean = false,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<List<JsonObject>>

}

@Serializable
data class WordItem(
    val word: String,
    val id: Int,
)


data class Definition(
    val text: String
)
