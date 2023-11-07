package com.rodcollab.brupapp.data

import com.rodcollab.brupapp.BuildConfig
import com.rodcollab.brupapp.di.WordnikConnection
import com.rodcollab.brupapp.hangman.repository.WordAnswer
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
    suspend fun questionItems(): List<WordAnswer>

}

class NetworkRandomWordsImpl : NetworkRandomWords {

    override suspend fun questionItems(): List<WordAnswer> {
        val response = withContext(Dispatchers.IO) { async { service.randomWords() }.await() }
        var randomWords = listOf<WordItem>()
        val itemQuestions = mutableListOf<WordAnswer>()
        if (response.isSuccessful) {
            randomWords = service.randomWords().body()!!
        }

        randomWords.map { word ->
            val responseDefinition = withContext(Dispatchers.IO) { async { service.definitions(word.word) }.await() }
            if (responseDefinition.isSuccessful) {
                responseDefinition.body()!!.map { jsonObject ->
                    var text = jsonObject["text"].toString()
                    if (text.contains("<xref>")) {
                        val newText = text.split("<xref>").toMutableList()
                        text = newText.first()
                        newText.remove(newText.first())
                        newText.map {
                            val nText = it.split("</xref>").toMutableList()
                            text += nText.first() + nText.last()
                        }
                    } else if (text.contains("<em>")) {
                        val newText = text.split("<em>").toMutableList()
                        text = newText.first()
                        newText.remove(newText.first())
                        newText.map {
                            val textSplit = it.split("</em>").toMutableList()
                            if (word.word.lowercase() == textSplit.first().lowercase()) {
                                val targetWord = textSplit.first()
                                var textAnswer = ""
                                targetWord.map {
                                    textAnswer += "_"
                                }
                                text += textAnswer + textSplit.last()
                            }
                        }
                    }
                    itemQuestions.add(WordAnswer(value = word.word, definition = text))
                }
            }
        }
        return itemQuestions
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
