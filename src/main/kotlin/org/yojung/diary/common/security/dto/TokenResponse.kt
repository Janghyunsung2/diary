package org.yojung.diary.common.security.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
