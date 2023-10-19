package com.rodcollab.brupapp.di

import com.rodcollab.brupapp.hangman.repository.HangmanGame
import com.rodcollab.brupapp.hangman.repository.HangmanGameImpl

interface AppContainer {
    val hangmanGame: HangmanGame
}

class AppContainerImpl : AppContainer {
    override val hangmanGame: HangmanGame by lazy { HangmanGameImpl.getInstance() }
}