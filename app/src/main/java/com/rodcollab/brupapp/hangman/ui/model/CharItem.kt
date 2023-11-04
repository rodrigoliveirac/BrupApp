package com.rodcollab.brupapp.hangman.ui.model

data class CharItem(
    val char: String,
    val guessed: Boolean,
) {
    val isLetter: Boolean = isLetter(char.toCharArray().first())
    val alpha = if(guessed || !isLetter) 1.0f else 0.0f
}

fun List<Char>.toCharItem(usedLetters: List<Char>) = map { char ->
    CharItem(
        char = char.uppercase(),
        guessed = usedLetters.any { it == char }
    )
}

private fun isLetter(c: Char): Boolean = when (c) {
    in 'a'..'z', in 'A'..'Z' -> true
    else -> false
}