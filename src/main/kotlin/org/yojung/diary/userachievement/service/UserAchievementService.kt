package org.yojung.diary.userachievement.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse
import org.yojung.diary.userachievement.dto.UserAchievementUpdateRequest

interface UserAchievementService {
    fun registerUserAchievement(request: UserAchievementRegisterRequest): UserAchievementResponse
    fun getUserAchievement(id: Long): UserAchievementResponse
    fun patchUserAchievement(code: String, userId: Long): UserAchievementResponse
    fun getUserAchievements(pageable: Pageable, userId: Long): Page<UserAchievementResponse>

    fun grantReward(code: String, userId: Long)
}

