package org.yojung.diary.user.dto

import jakarta.validation.constraints.NotBlank
import org.yojung.diary.user.domain.Gender

// 회원 정보 수정 요청 DTO
data class UserUpdateRequest(
    @field:NotBlank
    val nickname: String,
    @field:NotBlank
    val profileImage: String,
    val gender: Gender? = null
)

