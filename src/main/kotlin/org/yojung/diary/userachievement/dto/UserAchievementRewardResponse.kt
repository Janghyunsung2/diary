package org.yojung.diary.userachievement.dto

// 유저 업적 보상 응답 DTO
data class UserAchievementRewardResponse(
    val userId: Long,
    val achievementCode: String,
    val rewardAmount: Int
)

