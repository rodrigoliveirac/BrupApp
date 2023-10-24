package com.rodcollab.brupapp.data

import com.rodcollab.brupapp.BuildConfig
import com.rodcollab.brupapp.di.WordnikConnection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkRandomWords {
    suspend fun randomWords(): List<WordItem>
    suspend fun definition(word: String): List<Definition>?
}

class NetworkRandomWordsImpl : NetworkRandomWords {

    override suspend fun randomWords(): List<WordItem> {
        if (service.randomWords().isSuccessful) {
            return service.randomWords().body()!!
        }
        return service.randomWords().body()!!
    }

    override suspend fun definition(word: String): List<Definition>? {
        if (service.definitions(word).isSuccessful) {
            return service.definitions(word).body()
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

    @GET("words.json/randomWords?limit=5&api_key=${BuildConfig.API_KEY}")
    suspend fun randomWords(): Response<ArrayList<WordItem>>

    @GET("word.json/{word}/definitions")
    suspend fun definitions(
        @Path("word") word: String,
        @Query("limit") limit: Int = 10,
        @Query("includeRelated") includeRelated: Boolean = false,
        @Query("useCanonical") useCanonical: Boolean = false,
        @Query("includeTags") includeTags: Boolean = false,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<ArrayList<Definition>>

}

@Serializable
data class WordItem(
    val word: String,
    val id: Int,
)

@Serializable
data class Definition(
    @SerialName("id")
    val id: String? = null,
    @SerialName("partOfSpeech")
    val partOfSpeech: String? = null,
    @SerialName("attributionText")
    val attributionText: String? = null,
    @SerialName("sourceDictionary")
    val sourceDictionary: String? = null,
    @SerialName("text")
    val text: String? = null,
    @SerialName("sequence")
    val sequence: String? = null,
    @SerialName("score")
    val score: Int? = null,
    @SerialName("word")
    val word: String? = null,
    @SerialName("attributionUrl")
    val attributionUrl: String? = null,
    @SerialName("wordnikUrl")
    val wordnikUrl: String? = null,
    @SerialName("citations")
    val citations: List<JsonObject>? = null,
    @SerialName("exampleUses")
    val exampleUses: List<JsonObject>? = null,
    @SerialName("labels")
    val labels: List<JsonObject>? = null,
    @SerialName("notes")
    val notes: List<JsonObject>? = null,
    @SerialName("relatedWords")
    val relatedWords: List<JsonObject>? = null,
    @SerialName("textProns")
    val textProns: List<JsonObject>? = null
)
