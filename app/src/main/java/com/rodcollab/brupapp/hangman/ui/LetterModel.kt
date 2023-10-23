package com.rodcollab.brupapp.hangman.ui

data class LetterModel(
    val char: Char,
    val isEnabled: Boolean,
    val isSelected: Boolean
)