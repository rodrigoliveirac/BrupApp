package com.rodcollab.brupapp.hangman.ui.model

data class LetterModel(
    val char: Char,
    val isEnabled: Boolean,
    val isSelected: Boolean
)