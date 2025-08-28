package org.yojung.diary.common.security.dto

data class OauthLoginRequest(
    val provider: String?,
    val providerId: String?,
)
