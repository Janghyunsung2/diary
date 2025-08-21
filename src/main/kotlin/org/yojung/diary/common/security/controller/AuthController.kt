package org.yojung.diary.common.security.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.yojung.diary.common.security.dto.ApiResponse
import org.yojung.diary.common.security.dto.JwtResponse
import org.yojung.diary.common.security.dto.LoginRequest
import org.yojung.diary.common.security.service.AuthService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        val jwtResponse = authService.authenticateUser(loginRequest)
        return ResponseEntity.ok(jwtResponse)
    }

    @PostMapping("/admin/login")
    fun authenticateAdmin(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        // 동일한 인증 로직이지만 명시적으로 관리자 로그인 엔드포인트
        val jwtResponse = authService.authenticateUser(loginRequest)
        return ResponseEntity.ok(jwtResponse)
    }

    @PostMapping("/logout")
    fun logoutUser(): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(ApiResponse(true, "User logged out successfully"))
    }
}
