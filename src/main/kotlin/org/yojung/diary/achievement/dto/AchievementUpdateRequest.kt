package org.yojung.diary.achievement.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile


data class AchievementUpdateRequest (
    var code: String,

    var name: @NotBlank kotlin.String,

    var nextCode: kotlin.String?,

    var description: @NotBlank kotlin.String,

    var creditReward: @Min(1) kotlin.Int,

    var progressValue: @Min(1) kotlin.Int,

    var image: MultipartFile,
)
