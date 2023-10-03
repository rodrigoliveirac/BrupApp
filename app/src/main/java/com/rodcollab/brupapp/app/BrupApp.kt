package com.rodcollab.brupapp.app

import android.app.Application
import com.rodcollab.brupapp.data.oop
import com.rodcollab.brupapp.data.programming
import com.rodcollab.brupapp.hangman.repository.HangmanGame
import com.rodcollab.brupapp.hangman.repository.HangmanGameImpl

class BrupApp : Application() {

    lateinit var hangmanGame: HangmanGame

    override fun onCreate() {
        super.onCreate()
        val dataSet = oop + programming
        hangmanGame = HangmanGameImpl(dataSet)
    }
}