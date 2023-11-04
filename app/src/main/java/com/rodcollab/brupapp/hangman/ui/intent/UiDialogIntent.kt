package com.rodcollab.brupapp.hangman.ui.intent

sealed interface UiDialogIntent {
    object StartNewGame : UiDialogIntent
    object NextWord : UiDialogIntent
    data class DisplayPerformance(val display: Boolean) : UiDialogIntent
    object RefreshConnection : UiDialogIntent
}