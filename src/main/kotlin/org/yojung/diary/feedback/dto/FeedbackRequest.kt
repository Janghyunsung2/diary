package org.yojung.diary.feedback.dto

data class FeedbackRequest(
    val content: String,
    val nickname: String,
    val prompt: String,
    val isUseCredit: Boolean
)
