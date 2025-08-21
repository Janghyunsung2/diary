package org.yojung.diary.userachievement.dto

// 유저 업적 응답 DTO
data class UserAchievementResponse(
    val id: Long?,
    val userId: Long,
    val achievementCode: String,
    val currentValue: Int,
    val goalValue: Int,
    val completed: Boolean
)

