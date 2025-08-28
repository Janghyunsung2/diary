package org.yojung.diary.achievement.controller

import org.hibernate.annotations.Parameter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.yojung.diary.achievement.dto.AchievementRegisterRequest
import org.yojung.diary.achievement.dto.AchievementResponse
import org.yojung.diary.achievement.service.AchievementService


@RestController
@RequestMapping("/api/achievements")
class AchievementController {
    private val achievementService: AchievementService

    @Autowired
    constructor(achievementService: AchievementService) {
        this.achievementService = achievementService
    }

    @PostMapping
    @kotlin.Throws(java.lang.Exception::class)
    fun registerAchievement(
        @RequestBody @Validated request: AchievementRegisterRequest
    ): ResponseEntity<AchievementResponse> {
        val achievementResponse: AchievementResponse = achievementService.registerAchievement(request)
        return ResponseEntity.ok<AchievementResponse>(achievementResponse)
    }

    @GetMapping
    @kotlin.Throws(java.lang.Exception::class)
    fun getAllAchievements(pageable: Pageable): ResponseEntity<Page<AchievementResponse>> {
        val achievements: Page<AchievementResponse> =
            achievementService.getAchievements(pageable)
        return ResponseEntity.ok<Page<AchievementResponse>>(achievements)
    }

    @GetMapping("/{code}")
    @kotlin.Throws(java.lang.Exception::class)
    fun getAchievement(
        @PathVariable(name = "code") code: kotlin.String
    ): ResponseEntity<AchievementResponse?> {
        val achievementResponse: AchievementResponse = achievementService.getAchievement(code)
        return ResponseEntity.ok<AchievementResponse>(achievementResponse)
    }

}