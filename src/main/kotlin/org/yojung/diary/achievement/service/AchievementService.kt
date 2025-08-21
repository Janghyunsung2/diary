package org.yojung.diary.achievement.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.achievement.dto.AchievementRegisterRequest
import org.yojung.diary.achievement.dto.AchievementResponse
import org.yojung.diary.achievement.dto.AchievementUpdateRequest

interface AchievementService {
    @Throws(Exception::class)
    fun registerAchievement(request: AchievementRegisterRequest): AchievementResponse

    @Throws(Exception::class)
    fun getAchievement(code: String): AchievementResponse

    @Throws(Exception::class)
    fun updateAchievement(code: String, request: AchievementUpdateRequest): AchievementResponse

    @Throws(Exception::class)
    fun getAchievements(pageable: Pageable): Page<AchievementResponse>

    fun deleteAchievement(code: String)
}
