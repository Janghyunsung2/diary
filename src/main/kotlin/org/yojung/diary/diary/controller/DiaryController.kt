package org.yojung.diary.diary.controller

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.yojung.diary.common.security.CustomUserDetails
import org.yojung.diary.diary.dto.DiaryRegisterRequest
import org.yojung.diary.diary.dto.DiaryUpdateRequest
import org.yojung.diary.diary.service.DiaryService

@RestController
@RequestMapping("/api/dailies")
class DiaryController (private val diaryService: DiaryService) {

    @PostMapping
    fun createDiary(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestBody diaryRegisterRequest: DiaryRegisterRequest) : ResponseEntity<Any> {
        val response = diaryService.createDaily(user.getId(),diaryRegisterRequest)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{dailyId}")
    fun updateDiary(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable dailyId: Long,
        @RequestBody request: DiaryUpdateRequest) : ResponseEntity<Any> {
        val response = diaryService.updateDaily(dailyId, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{dailyId}")
    fun deleteDiary(
        @PathVariable dailyId: Long) : ResponseEntity<Any> {
        diaryService.deleteDaily(dailyId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun getDiaries(
        @AuthenticationPrincipal user: CustomUserDetails,
        pageable: Pageable
    ) : ResponseEntity<Any> {
        val response = diaryService.getDailyByUserId(user.getId(), pageable)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{dailyId}")
    fun getDiary(
        @AuthenticationPrincipal user: CustomUserDetails,
        @PathVariable dailyId: Long
    ) : ResponseEntity<Any> {
        val response = diaryService.getDailyAndFeedback(user.getId(),dailyId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/month")
    fun getDiariesByMonth(
        @AuthenticationPrincipal user: CustomUserDetails,
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Int
    ) : ResponseEntity<Any> {
        val response = diaryService.getDiaryByMonth(user.getId(), year, month)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/can-write")
    fun canWriteDiary(
        @AuthenticationPrincipal user: CustomUserDetails,
    ) : ResponseEntity<Any> {
        val response = diaryService.canWriteDaily(user.getId())
        return ResponseEntity.ok(response)
    }


}