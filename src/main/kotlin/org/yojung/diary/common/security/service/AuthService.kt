package org.yojung.diary.common.security.service

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.yojung.diary.common.security.CustomUserDetailsService
import org.yojung.diary.common.security.JwtTokenProvider
import org.yojung.diary.common.security.dto.OauthLoginRequest
import org.yojung.diary.common.security.dto.TokenRequest
import org.yojung.diary.common.security.dto.TokenResponse

@Service
class AuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val customUserDetailsService: CustomUserDetailsService
) {
    var logger = LoggerFactory.getLogger(AuthService::class.java)

    fun oauthLogin(oauthLoginRequest: OauthLoginRequest): TokenResponse {
        val oauthId = oauthLoginRequest.providerId
        val provider = oauthLoginRequest.provider

        // 1) 유저 로드 (너 CustomUserDetailsService가 oauthId로 찾도록 돼있음)
        val userDetails = customUserDetailsService.loadByProviderAndProviderId(provider,oauthId)

        // 2) 인증객체 수동 생성 + 컨텍스트에 주입
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
        SecurityContextHolder.getContext().authentication = authentication

        // 3) 토큰 발급
        val accessToken = jwtTokenProvider.generateToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)
        logger.info("Access token: $accessToken")

        return TokenResponse(accessToken, refreshToken)
    }

    fun refreshToken(tokenRequest: TokenRequest): TokenResponse? {
        val rt = tokenRequest.refreshToken ?: return null
        if (!jwtTokenProvider.validateToken(rt)) return null

        // 리프레시 토큰 subject로 유저 로드 → 새 인증객체 생성
        val emailOrOauthId = jwtTokenProvider.getProviderIdFromToken(rt)
        val userDetails = customUserDetailsService.loadUserByUsername(emailOrOauthId)
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
        SecurityContextHolder.getContext().authentication = authentication

        val newAccess = jwtTokenProvider.generateToken(authentication)
        val newRefresh = jwtTokenProvider.generateRefreshToken(authentication)
        return TokenResponse(newAccess, newRefresh)
    }
}