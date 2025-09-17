package org.yojung.diary.userachievement.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.achievement.repository.AchievementRepository
import org.yojung.diary.credittransaction.dto.CreditTransactionRegisterRequest
import org.yojung.diary.credittransaction.service.CreditTransactionService
import org.yojung.diary.userachievement.domain.UserAchievement
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse
import org.yojung.diary.userachievement.exception.UserAchievementNotFoundException
import org.yojung.diary.userachievement.mapper.UserAchievementMapper
import org.yojung.diary.userachievement.repository.UserAchievementRepository
import org.yojung.diary.userachievement.service.UserAchievementService

@Service
class UserAchievementServiceImpl(
    private val userAchievementRepository: UserAchievementRepository,
    private val userAchievementMapper: UserAchievementMapper,
    private val achievementRepository: AchievementRepository,
    private val creditTransactionService: CreditTransactionService
) : UserAchievementService {

    @Transactional
    override fun registerUserAchievement(request: UserAchievementRegisterRequest): UserAchievementResponse {

        val achievement = achievementRepository.findById(request.achievementCode)
            .orElseThrow();
        val userAchievement = UserAchievement(
            userId = request.userId,
            achievement = achievement,
            currentValue = 1,
            goalValue = achievement.progressValue,
            completed = false,
            rewardGranted = false,
        )

        userAchievementRepository.save(userAchievement)

        return userAchievementMapper.toResponse(userAchievement)
    }

    @Transactional(readOnly = true)
    override fun getUserAchievement(id: Long): UserAchievementResponse {
        val entity = userAchievementRepository.findById(id)
            .orElseThrow { UserAchievementNotFoundException(id) }
        return userAchievementMapper.toResponse(entity)
    }

    @Transactional
    override fun patchUserAchievement(
        code: String,
        userId: Long
    ): UserAchievementResponse {
        var userAchievement = userAchievementRepository.findByCode(code);
        if(userAchievement == null){
            val request = UserAchievementRegisterRequest(
                userId = userId,
                achievementCode = code,
            )
            registerUserAchievement(request)
        }
        userAchievement?.increaseProgress();

        return userAchievementMapper.toResponse(userAchievement);
    }

    @Transactional(readOnly = true)
    override fun getUserAchievements(
        pageable: Pageable,
        userId: Long
    ): Page<UserAchievementResponse> {
        return userAchievementRepository.findAllByUserId(pageable, userId)
            .stream()
            .map { ua -> userAchievementMapper.toResponse(ua) }
            .let {
                org.springframework.data.domain.PageImpl(it.toList(), pageable, it.count())
            }
    }

    override fun grantReward(code: String, userId: Long) {
        val userAchievement = userAchievementRepository.findByCode(code);
        if(userAchievement == null){
            throw UserAchievementNotFoundException(code);
        }
        if(userAchievement.rewardGranted || !userAchievement.completed){
            throw IllegalStateException("Cannot grant reward");
        }
        userAchievement.grantReward();
        creditTransactionService.registerTransaction(
            CreditTransactionRegisterRequest(
                userId = userId,
                amount = userAchievement.achievement?.creditReward,
                reason = "Achievement reward for ${userAchievement.achievement?.name}",
                achievementCode  = code
            )
        )
    }

}

