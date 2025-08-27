package org.yojung.diary.common.security.dto

import org.yojung.diary.user.domain.Gender

data class AuthResponse(
    val userId: Long,
    val nickName: String,
    val profileImageUrl: String,
    val gender: Gender,
    val provider: String,
    val providerId: String,
    )
