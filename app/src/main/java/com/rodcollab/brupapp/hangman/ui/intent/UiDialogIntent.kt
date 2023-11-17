package com.rodcollab.brupapp.hangman.ui.intent

sealed interface UiDialogIntent {

    object StartNewGame : UiDialogIntent
    object RestartGame : UiDialogIntent
    object NextWord : UiDialogIntent
    data class DisplayPerformance(val display: Boolean) : UiDialogIntent
    data class DisplayReview(val display: Boolean) : UiDialogIntent
    object RefreshConnection : UiDialogIntent
}