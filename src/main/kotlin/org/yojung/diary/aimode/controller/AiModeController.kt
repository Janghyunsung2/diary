package org.yojung.diary.aimode.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.yojung.diary.aimode.dto.AiModeRegisterRequest
import org.yojung.diary.aimode.dto.AiModeResponse
import org.yojung.diary.aimode.dto.AiModeUpdateRequest
import org.yojung.diary.aimode.service.AiModeService

@RestController
@RequestMapping("/api/ai-modes")
class AiModeController(
    private val aiModeService: AiModeService
) {

    @GetMapping("/{id}")
    fun getAiMode(@PathVariable id: Int): ResponseEntity<AiModeResponse> {
        val response = aiModeService.getAiMode(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getAiModes(pageable: Pageable): ResponseEntity<Page<AiModeResponse>> {
        val response = aiModeService.getAiModes(pageable)
        return ResponseEntity.ok(response)
    }
}

