package org.yojung.diary.userachievement.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse
import org.yojung.diary.userachievement.dto.UserAchievementUpdateRequest

interface UserAchievementService {
    fun registerUserAchievement(request: UserAchievementRegisterRequest): UserAchievementResponse
    fun getUserAchievement(id: Long): UserAchievementResponse
    fun updateUserAchievement(id: Long, request: UserAchievementUpdateRequest): UserAchievementResponse
    fun getUserAchievements(pageable: Pageable): Page<UserAchievementResponse>
}

