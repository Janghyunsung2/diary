package org.yojung.diary.common.security.dto

data class TokenRequest(
    val accessToken: String,
    val refreshToken: String,
)