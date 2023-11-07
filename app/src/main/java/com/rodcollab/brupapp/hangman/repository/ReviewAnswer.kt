package com.rodcollab.brupapp.hangman.repository

interface ReviewAnswer {
    fun addReviewAnswer(reviewAnswerModel: AnswerModel)
    fun reviewAnswers(): List<AnswerModel>

    fun clear()
}

class ReviewAnswerImpl : ReviewAnswer {

    private val reviewAnswers = Array(5) { AnswerModel(word = "",isCorrect = true) }
    private var count: Int = 0

    override fun addReviewAnswer(reviewAnswerModel: AnswerModel) {
        reviewAnswers[count] = reviewAnswerModel
        count++
    }

    override fun reviewAnswers(): List<AnswerModel> = reviewAnswers.toList()

    override fun clear() {
        count = 0
    }


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