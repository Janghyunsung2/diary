package org.yojung.diary.achievement.dto

import java.time.ZonedDateTime


data class AchievementResponse (
    var code: String,

    var nextCode: String?,

    var name: String,

    var description: String,

    var creditReward: Int,

    var progressValue: Int,

    var createdAt: ZonedDateTime?,

    var iconUrl: String?
)
