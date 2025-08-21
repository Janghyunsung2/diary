package org.yojung.diary.userachievement.dto

import jakarta.validation.constraints.NotNull

// 유저 업적 수정 요청 DTO
data class UserAchievementUpdateRequest(
    @field:NotNull
    val currentValue: Int
)

