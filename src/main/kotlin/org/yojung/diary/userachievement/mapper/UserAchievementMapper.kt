package org.yojung.diary.userachievement.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.yojung.diary.userachievement.domain.UserAchievement
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse

@Mapper(componentModel = "spring")
interface UserAchievementMapper {
    fun toEntity(request: UserAchievementRegisterRequest): UserAchievement

    @Mapping(target = "iconUrl", source = "achievement.iconUrl")
    @Mapping(target = "achievementName", source = "achievement.name")
    @Mapping(target = "achievementCode", source = "achievement.code")
    @Mapping(target = "creditReward", source = "achievement.creditReward")
    fun toResponse(entity: UserAchievement?): UserAchievementResponse
}

