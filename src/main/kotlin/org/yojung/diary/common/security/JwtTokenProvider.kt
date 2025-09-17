package org.yojung.diary.common.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.Base64.getDecoder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import kotlin.text.get

@Component
class JwtTokenProvider(
    private val userDetailsService: CustomUserDetailsService // ⬅ provider+providerId로 찾는 메서드 필요
) {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration:86400000}")
    private var jwtExpirationMs: Long = 86400000

    private lateinit var key: Key

    @PostConstruct
    fun init() {
        val raw = jwtSecret.trim()
        val secretBytes = try {
            if (raw.matches(Regex("^[A-Za-z0-9+/=]+$"))) {
                val decoded = getDecoder().decode(raw)
                if (decoded.size >= 64) decoded else raw.toByteArray()
            } else raw.toByteArray()
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
            .setSubject(principal.getEmail())   // ✅ providerId를 subject로
            .claim("id", principal.getId())
            .claim("providerId", principal.getEmail())
            .claim("role", principal.getRole())
            .claim("isAdmin", principal.isAdmin())
            .claim("provider", principal.getProvider()) // provider 정보도 클레임에 추가
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun generateRefreshToken(authentication: Authentication): String {
        val principal = authentication.principal as CustomUserDetails
        val refreshExpirationMs = jwtExpirationMs * 7 * 24
        val expiryDate = Date(System.currentTimeMillis() + refreshExpirationMs)

        return Jwts.builder()
            .setSubject(principal.getEmail())
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getProviderIdFromToken(token: String): String =
        Jwts.parser().setSigningKey(key).build()
            .parseClaimsJws(token).body.subject


    fun getClaim(token: String, name: String): Any? {
        return try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).body[name]
        } catch (e: ExpiredJwtException) {
            e.claims[name]
        }
    }


    fun getProviderFromToken(token: String): String {
        val claims = Jwts.parser().setSigningKey(key).build()
            .parseClaimsJws(token).body
        return claims["provider"]?.toString() ?: error("provider claim 없음")
    }

    fun getClaims(token: String) =
        Jwts.parser().setSigningKey(key).build()
            .parseClaimsJws(token).body

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (_: Exception) { false }


    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val provider = claims["provider"]?.toString() ?: error("provider claim 없음")
        val providerId = claims.subject
        val userDetails = userDetailsService.loadByProviderAndProviderId(provider, providerId)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }
}