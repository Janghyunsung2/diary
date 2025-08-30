package org.yojung.diary.feedback.dto

// 응답 DTO
data class FeedbackResponse(
    val content: String,
    val mode: String?,
    var imageUrl: String?,
)
