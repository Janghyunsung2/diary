package org.yojung.diary.common.security.service

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.yojung.diary.common.security.JwtTokenProvider

import org.yojung.diary.common.security.dto.OauthLoginRequest
import org.yojung.diary.common.security.dto.TokenRequest
import org.yojung.diary.common.security.dto.TokenResponse


@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    var logger: org.slf4j.Logger = LoggerFactory.getLogger(AuthService::class.java)

    fun oauthLogin(oauthLoginRequest: OauthLoginRequest): TokenResponse? {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                oauthLoginRequest.provider + "_" + oauthLoginRequest.providerId,
                null
            )
        )
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val accessToken = jwtTokenProvider.generateToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)
        logger.info("Access token: $accessToken")
        return TokenResponse(accessToken, refreshToken)
    }

    fun refreshToken(tokenRequest: TokenRequest): TokenResponse? {
        tokenRequest.refreshToken?.let {
            if (jwtTokenProvider.validateToken(it)) {
                val authentication: Authentication = jwtTokenProvider.getAuthentication(tokenRequest.refreshToken)
                val accessToken = jwtTokenProvider.generateToken(authentication)
                val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)
                return TokenResponse(accessToken, refreshToken)
            }
        }
        return null
    }


}
