package com.rodcollab.brupapp.app

interface SharedPreferencesHandler {

    suspend fun edit(key: String, value: String)

    fun getString(key: String?): String?
}