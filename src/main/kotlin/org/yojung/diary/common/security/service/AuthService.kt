package org.yojung.diary.common.security.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.yojung.diary.common.security.CustomUserDetails
import org.yojung.diary.common.security.JwtTokenProvider
import org.yojung.diary.common.security.dto.JwtResponse
import org.yojung.diary.common.security.dto.LoginRequest

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun authenticateUser(loginRequest: LoginRequest): JwtResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )

        val userDetails = authentication.principal as CustomUserDetails
        val jwt = jwtTokenProvider.generateToken(authentication)

        return JwtResponse(
            token = jwt,
            userId = userDetails.getId(),
            email = userDetails.username,
            role = userDetails.getRole()
        )
    }
}
