package org.yojung.diary.userachievement.dto

// 유저 업적 달성 이벤트 DTO
data class UserAchievementCompletedEvent(
    val userId: Long,
    val achievementCode: String
)

