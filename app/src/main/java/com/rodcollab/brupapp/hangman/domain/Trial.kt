package com.rodcollab.brupapp.hangman.domain

data class Trial(
    val gameOn: Boolean = false,
    val gameOver: Boolean = false,
    val chars: List<Char> = listOf(),
    val usedLetters: List<Char> = listOf(),
    val chances: Int = 0,
    val tries: Int = 0,
    val hits: Int = 0,
    val errors: Int = 0,
    val answer: String = "",
    val tip: String = "",
    val gameIsFinish: Boolean = false,
    val newGame: Boolean = false,
    val performance: Float = 0.0f
)
