package org.yojung.diary.common.security.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.yojung.diary.common.security.CustomUserDetails

import org.yojung.diary.common.security.dto.OauthLoginRequest
import org.yojung.diary.common.security.dto.TokenRequest
import org.yojung.diary.common.security.dto.TokenResponse
import org.yojung.diary.common.security.service.AuthService
import org.yojung.diary.user.domain.User
import org.yojung.diary.user.dto.UserResponse
import org.yojung.diary.user.service.UserService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping("/oauth")
    fun oathLogin(@RequestBody oauthLoginRequest: OauthLoginRequest): ResponseEntity<TokenResponse> {
        val tokenResponse = authService.oauthLogin(oauthLoginRequest);
        return ResponseEntity.ok(tokenResponse)
    }

    @GetMapping("/users/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails : CustomUserDetails): ResponseEntity<UserResponse> {
        val userResponse = userService.getUser(userDetails.getId());

        return ResponseEntity.ok(userResponse)
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody tokenRequest: TokenRequest): ResponseEntity<TokenResponse> {
        // Logic to refresh JWT token
        val tokenResponse = authService.refreshToken(tokenRequest)
        return ResponseEntity.ok(tokenResponse)
    }

}
