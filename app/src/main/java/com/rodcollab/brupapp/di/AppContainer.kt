package com.rodcollab.brupapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rodcollab.brupapp.data.NetworkRandomWordsImpl
import com.rodcollab.brupapp.data.WordnikService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


abstract class WordnikConnection {

    private var instance: Retrofit? = null

    protected val service: WordnikService by lazy {
        retrofit("https://api.wordnik.com/v4/").create(
            WordnikService::class.java
        )
    }

    private fun retrofit(baseUrl: String): Retrofit {
        if (instance == null) {
            instance = Retrofit
                .Builder()
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .baseUrl(baseUrl)
                .build()
        }
        return instance!!
    }
}

object ConnectionObserver {

    var hasConnection = false
        private set

    fun updateConnection(hasConnection: Boolean) {
        this.hasConnection = hasConnection
    }

}

class AppContainerImpl() {
    init {
        NetworkRandomWordsImpl.getInstance()
    }
}