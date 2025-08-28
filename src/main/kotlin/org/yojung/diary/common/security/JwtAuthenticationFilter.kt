package org.yojung.diary.common.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    private val matcher = AntPathMatcher()

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val p = request.servletPath ?: request.requestURI
        return request.method.equals("OPTIONS", true) ||
                matcher.match("/api/auth/**", p) ||
                matcher.match("/api/public/**", p)
    }

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        try {
            val token = req.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.substring(7)
            if (!token.isNullOrBlank() && jwtTokenProvider.validateToken(token)) {
                val providerId = jwtTokenProvider.getProviderIdFromToken(token)
                val user = customUserDetailsService.loadUserByUsername(providerId)
                val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)
                auth.details = WebAuthenticationDetailsSource().buildDetails(req)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (e: Exception) {
            logger.warn("JWT filter error: ${e.message}")
        }
        chain.doFilter(req, res)
    }
}