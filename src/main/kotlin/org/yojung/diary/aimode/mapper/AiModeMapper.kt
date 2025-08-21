package org.yojung.diary.aimode.mapper

import org.mapstruct.Mapper
import org.yojung.diary.aimode.domain.AiMode
import org.yojung.diary.aimode.dto.AiModeRegisterRequest
import org.yojung.diary.aimode.dto.AiModeResponse

@Mapper(componentModel = "spring")
interface AiModeMapper {
    fun toResponse(aiMode: AiMode): AiModeResponse
    fun toEntity(aiModeRegisterRequest: AiModeRegisterRequest): AiMode
}

