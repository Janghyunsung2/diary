package org.yojung.diary.aimode.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

// 수정 요청 DTO
data class AiModeUpdateRequest(
    @field:NotBlank
    val mode: String,
    @field:NotBlank
    val label: String,
    @field:NotBlank
    val description: String,
    @field:NotNull
    val image: MultipartFile,
    @field:NotBlank
    val prompt: String,
    @field:NotBlank
    val colorLabel: String,
    @field:NotBlank
    val colorBg: String,
    @field:NotNull
    val creditAmount: Int
)

