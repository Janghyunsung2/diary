package org.yojung.diary.feedback.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.feedback.dto.FeedbackRegisterRequest
import org.yojung.diary.feedback.dto.FeedbackResponse

interface FeedbackService {
    fun registerFeedback(request: FeedbackRegisterRequest): FeedbackResponse
    fun getFeedback(id: Long): FeedbackResponse
    fun getFeedbacks(pageable: Pageable): Page<FeedbackResponse>
}

