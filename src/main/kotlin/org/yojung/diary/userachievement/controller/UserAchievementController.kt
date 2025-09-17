package org.yojung.diary.userachievement.controller

import com.sun.security.auth.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.yojung.diary.common.security.CustomUserDetails
import org.yojung.diary.userachievement.dto.UserAchievementRegisterRequest
import org.yojung.diary.userachievement.dto.UserAchievementResponse
import org.yojung.diary.userachievement.dto.UserAchievementUpdateRequest
import org.yojung.diary.userachievement.service.UserAchievementService

@RestController
@RequestMapping("/api/achievements")
class UserAchievementController(
    private val userAchievementService: UserAchievementService
) {

    @PatchMapping("/{code}/user-achievements")
    fun patchUserAchievement(
        @PathVariable code: String,
        @AuthenticationPrincipal user: CustomUserDetails,
    ): ResponseEntity<UserAchievementResponse> {
        val response = userAchievementService.patchUserAchievement(code, user.getId())
        return ResponseEntity.ok(response)
    }


    @GetMapping("/user-achievements/{id}")
    fun getUserAchievement(@PathVariable id: Long): ResponseEntity<UserAchievementResponse> {
        val response = userAchievementService.getUserAchievement(id)
        return ResponseEntity.ok(response)
    }


    @GetMapping("/user-achievements")
    fun getUserAchievements(pageable: Pageable, @AuthenticationPrincipal user: CustomUserDetails): ResponseEntity<Page<UserAchievementResponse>> {
        val response = userAchievementService.getUserAchievements(pageable, user.getId())
        return ResponseEntity.ok(response)
    }


    @PostMapping("/{code}/user-achievements/reward")
    fun grantReward(
        @PathVariable code: String,
        @AuthenticationPrincipal user: CustomUserDetails,
    ): ResponseEntity<Void> {
        val response = userAchievementService.grantReward(code, user.getId())
        return ResponseEntity.noContent().build<Void>()
    }
}

