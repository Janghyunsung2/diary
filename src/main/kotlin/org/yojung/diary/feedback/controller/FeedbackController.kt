package org.yojung.diary.feedback.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.yojung.diary.feedback.dto.FeedbackRegisterRequest
import org.yojung.diary.feedback.dto.FeedbackResponse
import org.yojung.diary.feedback.service.FeedbackService

@RestController
@RequestMapping("/api/feedbacks")
class FeedbackController(
    private val feedbackService: FeedbackService
) {
    @PostMapping
    fun registerFeedback(
        @RequestBody @Validated request: FeedbackRegisterRequest
    ): ResponseEntity<FeedbackResponse> {
        val response = feedbackService.registerFeedback(request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getFeedback(@PathVariable id: Long): ResponseEntity<FeedbackResponse> {
        val response = feedbackService.getFeedback(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getFeedbacks(pageable: Pageable): ResponseEntity<Page<FeedbackResponse>> {
        val response = feedbackService.getFeedbacks(pageable)
        return ResponseEntity.ok(response)
    }
}

