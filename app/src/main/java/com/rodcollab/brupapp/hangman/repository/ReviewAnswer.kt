package com.rodcollab.brupapp.hangman.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ReviewAnswer {
    suspend fun addReviewAnswer(reviewAnswerModel: AnswerModel)
    fun reviewAnswers(): List<AnswerModel>

    fun clear()
}

class ReviewAnswerImpl : ReviewAnswer {

    private var reviewAnswers = answerModels()
    private var count: Int = 0

    override suspend fun addReviewAnswer(reviewAnswerModel: AnswerModel) {
        withContext(Dispatchers.IO) {
            reviewAnswers[count] = reviewAnswerModel
            count++
        }
    }

    override fun reviewAnswers(): List<AnswerModel> = reviewAnswers.toList()

    override fun clear() {
        count = 0
        reviewAnswers = answerModels()
    }

    private fun answerModels() = Array(5) { AnswerModel(word = "", isCorrect = true) }


    companion object {
        private var instance: ReviewAnswer? = null
        fun getInstance(): ReviewAnswer {
            if (instance == null) {
                instance = ReviewAnswerImpl()
            }
            return instance!!
        }
    }

}

data class AnswerModel(
    val word: String,
    val isCorrect: Boolean
)