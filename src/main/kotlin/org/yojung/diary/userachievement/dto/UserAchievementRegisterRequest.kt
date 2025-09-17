package org.yojung.diary.userachievement.dto

import jakarta.validation.constraints.NotNull

// 유저 업적 등록 요청 DTO
data class UserAchievementRegisterRequest(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val achievementCode: String,
)

