package org.yojung.diary.common.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AdminAuthInterceptor : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val session = request.getSession(false)
        val isAdmin = session?.getAttribute("isAdmin") as? Boolean ?: false
        if (!isAdmin) {
            response.sendRedirect("/admin-server/login")
            return false
        }
        return true
    }
}