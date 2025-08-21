package org.yojung.diary.common.security.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String
)

data class JwtResponse(
    val token: String,
    val type: String = "Bearer",
    val userId: Long,
    val email: String,
    val role: String
)

data class ApiResponse(
    val success: Boolean,
    val message: String
)
