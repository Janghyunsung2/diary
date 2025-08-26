package org.yojung.diary.achievement.service.impl


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.achievement.domain.Achievement
import org.yojung.diary.achievement.dto.AchievementRegisterRequest
import org.yojung.diary.achievement.dto.AchievementResponse
import org.yojung.diary.achievement.dto.AchievementUpdateRequest
import org.yojung.diary.achievement.exception.AchievementNotFoundException
import org.yojung.diary.achievement.mapper.AchievementMapper
import org.yojung.diary.achievement.repository.AchievementRepository
import org.yojung.diary.achievement.service.AchievementService
import org.yojung.diary.storage.ObjectStorage

@Service
class AchievementServiceImpl : AchievementService {
    private val achievementRepository: AchievementRepository;
    private val achievementMapper: AchievementMapper
    private val objectStorage: ObjectStorage

    @Autowired
    constructor(
        achievementRepository: AchievementRepository,
        achievementMapper: AchievementMapper,
        objectStorage: ObjectStorage
    ) {
        this.achievementRepository = achievementRepository
        this.achievementMapper = achievementMapper
        this.objectStorage = objectStorage
    }

    @Transactional
    @Throws(Exception::class)
    override fun registerAchievement(request: AchievementRegisterRequest): AchievementResponse {
        val iconUrl: String? = objectStorage.uploadFile(request.image)
        val achievement: Achievement = achievementMapper.toEntity(request)
        iconUrl?.let { achievement.addIconUrl(it) }
        val savedAchievement: Achievement = achievementRepository.save(achievement)

        return achievementMapper.toResponse(savedAchievement)
    }


    @Transactional(readOnly = true)
    @Throws(Exception::class)
    override fun getAchievement(code: String): AchievementResponse {
        val achievement: Achievement = achievementRepository.findById(code)
            .orElseThrow({ AchievementNotFoundException(code) })
        return achievementMapper.toResponse(achievement)
    }


    @Transactional
    @Throws(Exception::class)
    override fun updateAchievement(code: String, request: AchievementUpdateRequest): AchievementResponse {
        val iconUrl: String? = objectStorage.uploadFile(request.image)
        val achievement: Achievement = achievementRepository.findById(code)
            .orElseThrow({ AchievementNotFoundException(code) })
        achievement.update(
            request.nextCode,
            request.name,
            request.description,
            request.creditReward,
            iconUrl,
            request.progressValue
        )
        return achievementMapper.toResponse(achievement)
    }

    @Transactional(readOnly = true)
    @Throws(Exception::class)
    override fun getAchievements(pageable: Pageable): Page<AchievementResponse> {
        return achievementRepository.findAll(pageable).map(achievementMapper::toResponse)
    }

    @Transactional
    override fun deleteAchievement(code: String) {
        achievementRepository.deleteAchievementByCode(code)
    }
}
