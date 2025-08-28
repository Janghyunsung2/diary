package org.yojung.diary.userachievement.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse
import org.yojung.diary.userachievement.dto.UserAchievementUpdateRequest
import org.yojung.diary.userachievement.service.UserAchievementService

@RestController
@RequestMapping("/api/achievements/user-achievements")
class UserAchievementController(
    private val userAchievementService: UserAchievementService
) {
    @PostMapping
    fun registerUserAchievement(
        @RequestBody @Validated request: UserAchievementRegisterRequest
    ): ResponseEntity<UserAchievementResponse> {
        val response = userAchievementService.registerUserAchievement(request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getUserAchievement(@PathVariable id: Long): ResponseEntity<UserAchievementResponse> {
        val response = userAchievementService.getUserAchievement(id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun updateUserAchievement(
        @PathVariable id: Long,
        @RequestBody @Validated request: UserAchievementUpdateRequest
    ): ResponseEntity<UserAchievementResponse> {
        val response = userAchievementService.updateUserAchievement(id, request)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getUserAchievements(pageable: Pageable): ResponseEntity<Page<UserAchievementResponse>> {
        val response = userAchievementService.getUserAchievements(pageable)
        return ResponseEntity.ok(response)
    }
}

