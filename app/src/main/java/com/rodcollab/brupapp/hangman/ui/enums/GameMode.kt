package com.rodcollab.brupapp.hangman.ui.enums

enum class GameMoveForward {
    RESET,
    VERIFY_ANSWER
}

enum class GameState {
    PREPARING,
    SHOW_RESPONSE,
    ENDED,
    DISPLAY_PERFORMANCE,
    NO_NETWORK,
    IDLE
}