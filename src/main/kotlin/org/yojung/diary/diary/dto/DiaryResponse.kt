package org.yojung.diary.diary.dto

import java.time.ZonedDateTime

// 일기 응답 DTO
data class DiaryResponse(
    val id: Long?,
    val content: String,
    val emotionType: String,
    val userId: Long,
    val createdAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?,
    val visibility: Boolean,
    val deleted: Boolean
)

