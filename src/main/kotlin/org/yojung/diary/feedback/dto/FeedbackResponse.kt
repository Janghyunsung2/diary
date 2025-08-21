package org.yojung.diary.feedback.dto

// 응답 DTO
data class FeedbackResponse(
    val id: Long?,
    val content: String,
    val mode: String,
    val diaryId: Long
)

