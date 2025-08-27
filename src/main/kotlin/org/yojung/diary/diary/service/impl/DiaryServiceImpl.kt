package org.yojung.diary.diary.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.aimode.exception.AiModeNotFoundException
import org.yojung.diary.aimode.repository.AiModeRepository
import org.yojung.diary.common.converter.EncryptConverter
import org.yojung.diary.credittransaction.service.CreditTransactionService
import org.yojung.diary.diary.ai.service.AiService
import org.yojung.diary.diary.domain.Diary
import org.yojung.diary.diary.dto.*
import org.yojung.diary.diary.exception.DiaryNotFoundException
import org.yojung.diary.diary.mapper.DiaryMapper
import org.yojung.diary.diary.repository.DiaryRepository
import org.yojung.diary.diary.service.DiaryService
import org.yojung.diary.feedback.domain.Feedback
import org.yojung.diary.feedback.dto.FeedbackRequest
import org.yojung.diary.feedback.exception.FeedbackNotFoundException
import org.yojung.diary.feedback.mapper.FeedbackMapper
import org.yojung.diary.feedback.repository.FeedbackRepository
import org.yojung.diary.logger.DiscordLogger
import org.yojung.diary.user.exception.UserNotFoundException
import org.yojung.diary.user.repository.UserRepository
import java.time.LocalDate

@Service
@Transactional
class DiaryServiceImpl(
    private val dailyRepository: DiaryRepository,
    private val aiModeRepository: AiModeRepository,
    private val feedbackRepository: FeedbackRepository,

    private val aiService: AiService,
    private val creditTransactionService: CreditTransactionService,
    private val feedbackMapper: FeedbackMapper,
    private val encryptConverter: EncryptConverter,
    private val discordLogger: DiscordLogger,
    private val diaryMapper: DiaryMapper,

    private val userRepository: UserRepository,
    // TODO: 레빗MQ 관련 의존성(feedbackConsumer, achievementConsumer) 제거 예정
) : DiaryService {
    @Transactional
    override fun createDaily(userId: Long, dailyRegisterRequest: DiaryRegisterRequest): DiaryResponse {
        val encryptedContent = encryptConverter.convertToDatabaseColumn(dailyRegisterRequest.content)
        val dailyRegisterRequest = dailyRegisterRequest.copy(content = encryptedContent)
        val user = userRepository.findById(userId).orElseThrow({ UserNotFoundException(userId) })
        var diary = diaryMapper.toEntity(dailyRegisterRequest, user);

        val saved = dailyRepository.save(diary)
        val aiMode = aiModeRepository.findById(dailyRegisterRequest.aiModeId)
            .orElseThrow { AiModeNotFoundException(dailyRegisterRequest.aiModeId) }
        val creditTransactionByMe = creditTransactionService.getCreditTransactionByMe(userId)
        if (creditTransactionByMe < aiMode.getCreditAmount()) {
            throw FeedbackNotFoundException("Insufficient credit to use AI mode")
        }
        val isUseCredit = aiMode.getCreditAmount() > 0

        val feedbackResponse = aiService.getFeedback(FeedbackRequest(
            content = dailyRegisterRequest.content,
            nickname = dailyRegisterRequest.nickname,
            prompt = aiMode.getPrompt(),
            isUseCredit = isUseCredit,
        ))

        val feedback = Feedback(
            diary = saved,
            content = feedbackResponse.content,
            mode = aiMode.getMode(),
        )
        feedbackRepository.save(feedback)
        discordLogger.info("일기 작성 로그: userId=$userId, nickname=${dailyRegisterRequest.nickname}")
        return diaryMapper.toResponse(saved)
    }

    @Transactional
    override fun updateDaily(dailyId: Long, dailyUpdateRequest: DiaryUpdateRequest): DiaryResponse {
        val daily = findDailyById(dailyId)
        daily.update(
            encryptConverter.convertToDatabaseColumn(dailyUpdateRequest.content),
            dailyUpdateRequest.emotionType,
            dailyUpdateRequest.visibility ?: daily.getVisibility(),
        )
        return diaryMapper.toResponse(daily)
    }

    @Transactional(readOnly = true)
    override fun getDaily(dailyId: Long): DiaryResponse {
        val daily = findDailyById(dailyId)
        return diaryMapper.toResponse(daily)
    }

    @Transactional
    override fun deleteDaily(dailyId: Long) {
        val daily = findDailyById(dailyId)
        daily.delete()
    }

    @Transactional(readOnly = true)
    override fun getDailies(pageable: Pageable): Page<DiaryResponse> {
        return dailyRepository.findAll(pageable).map(diaryMapper::toResponse)
    }

    @Transactional(readOnly = true)
    override fun getDailyByUserId(userId: Long, pageable: Pageable): Page<DiaryResponse> {
        return dailyRepository.findByUserId(userId, pageable).map { daily ->
            // 복호화 처리
            if (userId == daily.userId) {
                daily.copy(content = encryptConverter.convertToEntityAttribute(daily.content))
            } else daily
        }
    }

    @Transactional(readOnly = true)
    override fun getDailyAndFeedback(userId: Long, dailyId: Long): DiaryAndFeedbackResponse {
        val daily = findDailyById(dailyId)
        var dailyResponse = diaryMapper.toResponse(daily)


        dailyResponse = dailyResponse.copy(content = encryptConverter.convertToEntityAttribute(dailyResponse.content))

        val feedback = feedbackRepository.findByDiaryId(dailyId);
        val feedbackResponse = feedbackMapper.toResponse(feedback)

        return DiaryAndFeedbackResponse(dailyResponse, feedbackResponse)
    }

    @Transactional(readOnly = true)
    override fun getDiaryByMonth(userId: Long, year: Int, month: Int): List<DiaryResponse> {
        return dailyRepository.findByYearAndMonth(userId, year, month)
    }

    @Transactional(readOnly = true)
    override fun canWriteDaily(userId: Long): Boolean {
        return !dailyRepository.existsByUserIdAndCreatedAt(userId, LocalDate.now())
    }

    private fun findDailyById(dailyId: Long): Diary {
        return dailyRepository.findById(dailyId).orElseThrow { DiaryNotFoundException(dailyId) }
    }
}

