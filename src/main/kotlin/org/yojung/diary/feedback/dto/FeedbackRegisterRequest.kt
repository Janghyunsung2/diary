package org.yojung.diary.feedback.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

// 등록 요청 DTO
data class FeedbackRegisterRequest(
    @field:NotBlank
    val content: String,
    @field:NotBlank
    val mode: String,
    @field:NotNull
    val diaryId: Long
)

