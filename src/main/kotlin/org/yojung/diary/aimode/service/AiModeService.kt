package org.yojung.diary.aimode.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.aimode.dto.AiModeRegisterRequest
import org.yojung.diary.aimode.dto.AiModeResponse
import org.yojung.diary.aimode.dto.AiModeUpdateRequest

interface AiModeService {
    fun createAiMode(request: AiModeRegisterRequest): AiModeResponse
    fun updateAiMode(aiMode: String, request: AiModeUpdateRequest): AiModeResponse
    fun getAiMode(aiModeId: Int): AiModeResponse
    fun getAiModes(pageable: Pageable): Page<AiModeResponse>
}

