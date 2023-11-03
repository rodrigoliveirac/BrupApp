package com.rodcollab.brupapp.hangman.ui

import com.rodcollab.brupapp.hangman.ui.model.LetterModel

val alphabet = mutableListOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
)

internal fun MutableList<Char>.toLetterModels() = map { char ->
    LetterModel(
        char = char,
        isEnabled = true,
        isSelected = false
    )
}