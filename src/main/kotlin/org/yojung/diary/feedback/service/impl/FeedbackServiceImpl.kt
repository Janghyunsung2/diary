package org.yojung.diary.feedback.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.feedback.dto.FeedbackRegisterRequest
import org.yojung.diary.feedback.dto.FeedbackResponse
import org.yojung.diary.feedback.exception.FeedbackNotFoundException
import org.yojung.diary.feedback.mapper.FeedbackMapper
import org.yojung.diary.feedback.repository.FeedbackRepository
import org.yojung.diary.feedback.service.FeedbackService

@Service
class FeedbackServiceImpl(
    private val feedbackRepository: FeedbackRepository,
    private val feedbackMapper: FeedbackMapper
) : FeedbackService {

    @Transactional
    override fun registerFeedback(request: FeedbackRegisterRequest): FeedbackResponse {
        val entity = feedbackMapper.toEntity(request)
        val saved = feedbackRepository.save(entity)
        return feedbackMapper.toResponse(saved)
    }

    @Transactional(readOnly = true)
    override fun getFeedback(id: Long): FeedbackResponse {
        val entity = feedbackRepository.findById(id)
            .orElseThrow { FeedbackNotFoundException(id) }
        return feedbackMapper.toResponse(entity)
    }

    @Transactional(readOnly = true)
    override fun getFeedbacks(pageable: Pageable): Page<FeedbackResponse> {
        return feedbackRepository.findAll(pageable).map(feedbackMapper::toResponse)
    }
}

