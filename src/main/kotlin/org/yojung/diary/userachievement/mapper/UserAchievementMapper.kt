package org.yojung.diary.userachievement.mapper

import org.mapstruct.Mapper
import org.yojung.diary.achievement.domain.Achievement
import org.yojung.diary.userachievement.domain.UserAchievement
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse

@Mapper(componentModel = "spring")
interface UserAchievementMapper {
    fun toEntity(request: UserAchievementRegisterRequest): UserAchievement
    fun toResponse(entity: UserAchievement?): UserAchievementResponse
}

