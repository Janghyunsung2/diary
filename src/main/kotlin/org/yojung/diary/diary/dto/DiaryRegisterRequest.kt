package org.yojung.diary.diary.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

// 일기 등록 요청 DTO
data class DiaryRegisterRequest(
    val nickname: String,
    @field:NotBlank
    val content: String,
    @field:NotBlank
    val emotionType: String,
    @field:NotNull
    val visibility: Boolean = true,
    val aiModeId : Int = 0,
    val isUseCredit : Boolean = false,
)

