package org.yojung.diary.common.security.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.yojung.diary.common.security.dto.ApiResponse
import org.yojung.diary.common.security.dto.JwtResponse
import org.yojung.diary.common.security.dto.LoginRequest
import org.yojung.diary.common.security.dto.OauthLoginRequest
import org.yojung.diary.common.security.dto.TokenRequest
import org.yojung.diary.common.security.dto.TokenResponse
import org.yojung.diary.common.security.service.AuthService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/oauth")
    fun oathLogin(@RequestBody oauthLoginRequest: OauthLoginRequest): ResponseEntity<TokenResponse> {
        val tokenResponse = authService.oauthLogin(oauthLoginRequest);
        return ResponseEntity.ok(tokenResponse)
    }

    @GetMapping("/users/me")
    fun getCurrentUser(): ResponseEntity<ApiResponse> {
        // Logic to get current authenticated user details
        return ResponseEntity.ok(ApiResponse(true, "Current user details"))
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody tokenRequest: TokenRequest): ResponseEntity<TokenResponse> {
        // Logic to refresh JWT token
        val tokenResponse = authService.refreshToken(tokenRequest)
        return ResponseEntity.ok(tokenResponse)
    }

}
