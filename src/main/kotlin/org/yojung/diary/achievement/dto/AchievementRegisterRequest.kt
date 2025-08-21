package org.yojung.diary.achievement.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile


data class AchievementRegisterRequest (
    var code: @NotBlank kotlin.String,

    var name: @NotBlank kotlin.String,

    var description: @NotBlank kotlin.String,

    var creditReward: @Min(1) kotlin.Int,

    var progressValue: @Min(1) kotlin.Int,

    var image: MultipartFile,

    var nextCode: kotlin.String?= null,

    )
