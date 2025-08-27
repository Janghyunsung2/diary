package org.yojung.diary.common.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.Base64.getDecoder

@Component
class JwtTokenProvider {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration:86400000}")
    private var jwtExpirationMs: Long = 86400000

    private lateinit var key: Key

    @PostConstruct
    fun init() {
        val raw = jwtSecret.trim()
        val secretBytes = try {
            // Base64 로 보이는 경우 디코드 시도
            if (raw.matches(Regex("^[A-Za-z0-9+/=]+$"))) {
                val decoded = getDecoder().decode(raw)
                if (decoded.size >= 64) decoded else raw.toByteArray()
            } else {
                raw.toByteArray()
            }
        } catch (_: IllegalArgumentException) {
            raw.toByteArray()
        }

        require(secretBytes.size >= 64) {
            "jwt.secret 길이가 부족합니다. HS512 는 최소 64바이트 필요. 현재: ${secretBytes.size}바이트"
        }
        key = Keys.hmacShaKeyFor(secretBytes)
    }

    fun generateToken(authentication: Authentication): String {
        val principal = authentication.principal as CustomUserDetails
        val expiryDate = Date(System.currentTimeMillis() + jwtExpirationMs)

        return Jwts.builder()
            .setSubject(principal.username)
            .claim("userId", principal.getId())
            .claim("role", principal.getRole())
            .claim("isAdmin", principal.isAdmin())
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun generateRefreshToken(authentication: Authentication): String {
        val principal = authentication.principal as CustomUserDetails
        val refreshExpirationMs = jwtExpirationMs * 7 // 리프레시 토큰은 더 긴 만료 시간 설정
        val expiryDate = Date(System.currentTimeMillis() + refreshExpirationMs)

        return Jwts.builder()
            .setSubject(principal.username)
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserEmailFromToken(token: String): String =
        Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (_: Exception) {
            false
        }

    fun getAuthentication(token: String, authenticationManager: AuthenticationManager): Authentication {
        return SecurityContextHolder.getContext().authentication
    }
}