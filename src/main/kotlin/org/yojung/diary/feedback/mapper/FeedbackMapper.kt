package org.yojung.diary.feedback.mapper

import org.mapstruct.Mapper
import org.yojung.diary.feedback.domain.Feedback
import org.yojung.diary.feedback.dto.FeedbackRegisterRequest
import org.yojung.diary.feedback.dto.FeedbackResponse

@Mapper(componentModel = "spring")
interface FeedbackMapper {
    fun toEntity(request: FeedbackRegisterRequest): Feedback
    fun toResponse(entity: Feedback?): FeedbackResponse
}

