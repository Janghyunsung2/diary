package org.yojung.diary.user.dto

import org.yojung.diary.user.domain.Gender

// 회원 응답 DTO
data class UserResponse(
    val id: Long?,
    val oauthId: String?,
    val provider: String?,
    val nickname: String?,
    val profileImage: String?,
    val gender: Gender?
)

