package org.yojung.diary.diary.ai.service
import org.yojung.diary.feedback.dto.FeedbackRequest
import org.yojung.diary.feedback.dto.FeedbackResponse

interface AiService {
    fun getFeedback(feedbackRequest: FeedbackRequest): FeedbackResponse
}

