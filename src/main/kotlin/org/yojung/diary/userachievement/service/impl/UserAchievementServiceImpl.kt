package org.yojung.diary.userachievement.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.userachievement.domain.UserAchievement
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse
import org.yojung.diary.userachievement.dto.UserAchievementUpdateRequest
import org.yojung.diary.userachievement.exception.UserAchievementNotFoundException
import org.yojung.diary.userachievement.mapper.UserAchievementMapper
import org.yojung.diary.userachievement.repository.UserAchievementRepository
import org.yojung.diary.userachievement.service.UserAchievementService

@Service
class UserAchievementServiceImpl(
    private val userAchievementRepository: UserAchievementRepository,
    private val userAchievementMapper: UserAchievementMapper
) : UserAchievementService {

    @Transactional
    override fun registerUserAchievement(request: UserAchievementRegisterRequest): UserAchievementResponse {
        val entity = userAchievementMapper.toEntity(request)
        val saved = userAchievementRepository.save(entity)
        return userAchievementMapper.toResponse(saved)
    }

    @Transactional(readOnly = true)
    override fun getUserAchievement(id: Long): UserAchievementResponse {
        val entity = userAchievementRepository.findById(id)
            .orElseThrow { UserAchievementNotFoundException(id) }
        return userAchievementMapper.toResponse(entity)
    }

    @Transactional
    override fun updateUserAchievement(id: Long, request: UserAchievementUpdateRequest): UserAchievementResponse {
        val entity = userAchievementRepository.findById(id)
            .orElseThrow { UserAchievementNotFoundException(id) }
        entity.currentValue = request.currentValue
        val updated = userAchievementRepository.save(entity)
        return userAchievementMapper.toResponse(updated)
    }

    @Transactional(readOnly = true)
    override fun getUserAchievements(pageable: Pageable): Page<UserAchievementResponse> {
        return userAchievementRepository.findAll(pageable).map(userAchievementMapper::toResponse)
    }
}

