package org.yojung.diary.userachievement.dto

import java.time.ZonedDateTime

// 유저 업적 응답 DTO
data class UserAchievementResponse(
    val id: Long?,
    val userId: Long,
    val achievementName: String,
    val achievementCode: String,
    val currentValue: Int,
    val goalValue: Int,
    val creditReward: Int,
    val completed: Boolean,
    val rewardGranted: Boolean,
    val createdAt: ZonedDateTime,
    val iconUrl: String,
)

