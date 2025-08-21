package org.yojung.diary.feedback.dto

import java.time.LocalDate

data class FeedbackPagingResponse(
    val id: Long,
    val content: String,
    val mode: String,
    val dailyId: Long,
    val dailyContent: String,
    val emotionType: String,
    val userId: Long,
    val dailyCreatedAt: LocalDate,
    val visibility: Boolean
)

