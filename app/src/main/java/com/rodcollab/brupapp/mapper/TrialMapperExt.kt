package com.rodcollab.brupapp.mapper

import com.rodcollab.brupapp.hangman.domain.Trial
import com.rodcollab.brupapp.hangman.ui.HangmanGameUiState
import com.rodcollab.brupapp.hangman.ui.model.LetterModel
import com.rodcollab.brupapp.hangman.ui.model.toCharItem

fun Trial.toExternal(options: List<LetterModel>) =
    HangmanGameUiState(
        score = hashMapOf(
            "Tries: " to tries,
            "Chances left: " to chances,
            "Hits: " to hits,
            "Wrongs: " to errors,
            "Used letters: " to usedLetters.toString()
        ),
        gameOn,
        gameOver,
        chars.toCharItem(usedLetters),
        answer,
        letterOptions = options,
        tip,
        gameIsFinish,
        newGame,
        Pair(performance, "${(performance * 100).toInt()}%"),
        networkStatus = true,
        displaySeePerformanceButton = gameIsFinish,
    )