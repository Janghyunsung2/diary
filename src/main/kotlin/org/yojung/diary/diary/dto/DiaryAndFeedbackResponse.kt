package org.yojung.diary.diary.dto

import org.yojung.diary.feedback.dto.FeedbackResponse

data class DiaryAndFeedbackResponse(
    val dailyResponse: DiaryResponse,
    val feedbackResponse: FeedbackResponse?
)

