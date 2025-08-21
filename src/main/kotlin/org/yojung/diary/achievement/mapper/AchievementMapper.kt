package org.yojung.diary.achievement.mapper

import org.mapstruct.Mapper
import org.yojung.diary.achievement.domain.Achievement
import org.yojung.diary.achievement.dto.AchievementRegisterRequest
import org.yojung.diary.achievement.dto.AchievementResponse

@Mapper(componentModel = "spring")
interface AchievementMapper {

    fun toEntity(dto: AchievementRegisterRequest): Achievement

    fun toResponse(achievement: Achievement): AchievementResponse
}