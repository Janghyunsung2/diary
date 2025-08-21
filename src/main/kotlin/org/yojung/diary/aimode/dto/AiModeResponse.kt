package org.yojung.diary.aimode.dto

// 응답 DTO
data class AiModeResponse(
    val id: Long?,
    val mode: String,
    val label: String,
    val description: String,
    val imageUrl: String,
    val prompt: String,
    val colorLabel: String,
    val colorBg: String,
    val creditAmount: Int
)

