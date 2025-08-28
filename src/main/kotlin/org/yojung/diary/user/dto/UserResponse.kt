package org.yojung.diary.user.dto

import org.yojung.diary.user.domain.Gender

// 회원 응답 DTO
data class UserResponse(
    val userId: Long?,
    val providerId: String?,
    val provider: String?,
    val nickName: String?,
    val profileImageUrl: String?,
    val gender: Gender?
)

