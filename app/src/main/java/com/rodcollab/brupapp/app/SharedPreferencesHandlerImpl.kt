package com.rodcollab.brupapp.app

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferencesHandlerImpl(
    private val editor: SharedPreferences.Editor,
    private val sharedPrefs: SharedPreferences
) :
    SharedPreferencesHandler {

    override suspend fun edit(key: String, value: String) {
        withContext(Dispatchers.IO) {
            editor.putString(key, value)
        }
    }

    override fun getString(key: String?): String? =
        sharedPrefs.getString(key, "value")


}
