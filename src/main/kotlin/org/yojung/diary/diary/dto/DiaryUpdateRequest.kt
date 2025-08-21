package org.yojung.diary.diary.dto

import jakarta.validation.constraints.NotBlank

// 일기 수정 요청 DTO
data class DiaryUpdateRequest(
    @field:NotBlank
    val content: String,
    @field:NotBlank
    val emotionType: String,
    val visibility: Boolean? = null
)

