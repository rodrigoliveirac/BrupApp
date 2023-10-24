package com.rodcollab.brupapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rodcollab.brupapp.data.NetworkRandomWordsImpl
import com.rodcollab.brupapp.data.WordnikService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
}


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

class AppContainerImpl : AppContainer {
    init {
        NetworkRandomWordsImpl.getInstance()
    }
}