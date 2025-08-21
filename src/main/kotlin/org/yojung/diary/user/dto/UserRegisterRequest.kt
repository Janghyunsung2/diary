package org.yojung.diary.user.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.yojung.diary.user.domain.Gender

// 회원 등록 요청 DTO
data class UserRegisterRequest(
    @field:NotBlank
    val oauthId: String,
    @field:NotBlank
    val provider: String,
    @field:NotBlank
    val nickname: String,
    @field:NotBlank
    val profileImage: String,
    @field:NotNull
    val gender: Gender
)

